/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.xneox.epicguard.core.util;

import me.xneox.epicguard.core.config.migration.ConfigurationMigration;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.serialize.SerializationException;

/**
 * This class loads the configuration, maps values, and saves to the file.
 *
 * @param <C> the configuration class
 */
public class ConfigurationLoader<C> {
  private final HoconConfigurationLoader loader;
  private ObjectMapper<C> mapper;
  private final ConfigurationMigration[] migrations;

  public ConfigurationLoader(
          final @NotNull Class<C> implementation,
          final @NotNull HoconConfigurationLoader loader,
          final @NotNull ConfigurationMigration@NotNull... migrations
  ) {
    this.loader = loader;
    this.migrations = migrations;

    try {
      this.mapper = ObjectMapper.factory().get(implementation);
    } catch (SerializationException ex) {
      LogUtils.catchException("Couldn't create the object mapper for configuration: " + implementation.getSimpleName(), ex);
    }
  }

  private CommentedConfigurationNode applyMigration() throws ConfigurateException {
    final CommentedConfigurationNode rootNode = this.loader.load();
    boolean migrated = false;
    for (final ConfigurationMigration migration : migrations) {
      if (migration.shouldMigrate(rootNode)) {
        migration.migrate(rootNode);
        migrated = true;
      }
    }
    if (migrated) {
      this.loader.save(rootNode);
    }
    return rootNode;
  }

  @NotNull
  public C load() throws ConfigurateException {
    var configuration = this.mapper.load(this.applyMigration());
    this.save(configuration); // write default values
    return configuration;
  }

  public void save(final @NotNull C config) throws ConfigurateException {
    var node = this.loader.createNode();
    this.mapper.save(config, node);
    this.loader.save(node);
  }
}
