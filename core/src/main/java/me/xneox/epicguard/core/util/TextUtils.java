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

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import me.xneox.epicguard.core.EpicGuardAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class TextUtils {
  private TextUtils() {}
  private static final MiniMessage SERIALIZER = MiniMessage.miniMessage();
  private static final LoadingCache<String, Component> COMPONENT_CACHE = Caffeine.newBuilder()
          .expireAfterWrite(1, TimeUnit.MINUTES)
          .build(SERIALIZER::deserialize);

  /**
   * Constructs a {@link Component} from string.
   * Supports MiniMessage format
   *
   * @param message the original string
   * @return a component created from the string
   */
  @NotNull
  public static Component component(@NotNull String message) {
    return SERIALIZER.deserialize(message);
  }

  @NotNull
  public static Component cachedComponent(@NotNull String message) {
    return COMPONENT_CACHE.get(message);
  }

  /**
   * Builds a multiline {@link Component} from list of strings.
   */
  @NotNull
  public static Component multilineComponent(@NotNull List<String> list) {
    Objects.requireNonNull(list, "Kick message cannot be null!");

    final var builder = new StringBuilder();
    for (final String line : list) {
      builder.append(line).append('\n');
    }
    return cachedComponent(builder.toString());
  }

  /**
   * Tries to parse an {@link InetAddress} from the provided string.
   * For more information, see {@link InetAddress#getByName(String)}
   *
   * @param address String representation of a hostname
   * @return the parsed InetAddress
   */
  @Nullable
  public static InetAddress parseAddress(@NotNull String address) {
    try {
      return InetAddress.getByName(address);
    } catch (UnknownHostException ex) {
      EpicGuardAPI.INSTANCE.instance().logger().warn("Couldn't resolve the InetAddress for the host {}: {}", address, ex.getMessage());
    }
    return null;
  }
}
