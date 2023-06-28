package me.xneox.epicguard.core.config.migration;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public sealed interface ConfigurationMigration permits MiniMessageMigration {
    boolean shouldMigrate(final @NotNull ConfigurationNode rootNode);

    void migrate(final @NotNull ConfigurationNode rootNode) throws SerializationException;
}
