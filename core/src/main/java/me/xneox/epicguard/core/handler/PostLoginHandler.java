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

package me.xneox.epicguard.core.handler;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.util.TextUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Handler for the PlayerJoin or PostLogin listeners Used for the auto-whitelist feature,
 * and for SettingsCheck.
 */
public abstract class PostLoginHandler {
  private final EpicGuard epicGuard;
  private final Queue<UserInfo> whitelistedPlayers = new ConcurrentLinkedQueue<>();
  private final Queue<UserInfo> settingsPlayers = new ConcurrentLinkedQueue<>();

  protected PostLoginHandler(EpicGuard epicGuard) {
    this.epicGuard = epicGuard;

    epicGuard.platform().scheduleRepeatingTask(() -> {
      long currentTime = System.currentTimeMillis() / 1000;
      UserInfo info;
      while ((info = this.whitelistedPlayers.peek()) != null) {
        if (info.time() + this.epicGuard.config().autoWhitelist().timeOnline() > currentTime) {
          // All players after this one are too new to be checked.
          break;
        }

        whitelistedPlayers.remove();
        var user = this.epicGuard.userManager().get(info.uuid());

        // Check if the player has logged out
        if (user == null) {
          continue;
        }

        var meta = this.epicGuard.storageManager().addressMeta(info.address());
        meta.whitelisted(true);
      }
    }, 1);

    epicGuard.platform().scheduleRepeatingTask(() -> {
      long currentTime = System.currentTimeMillis() / 1000;
      UserInfo info;
      while ((info = this.settingsPlayers.peek()) != null) {
        if (info.time() + this.epicGuard.config().settingsCheck().delay() > currentTime) {
          // All players after this one are too new to be checked.
          break;
        }

        settingsPlayers.remove();
        var user = this.epicGuard.userManager().get(info.uuid());

        // Check if the player has logged out
        if (user == null) {
          continue;
        }

        if (!user.settingsChanged()) {
          this.epicGuard.platform().disconnectUser(info.uuid(), TextUtils.multilineComponent(this.epicGuard.messages().disconnect().settingsPacket()));
        }
      }
    }, 1);
  }

  /**
   * Handling the player who just have joined to the server.
   *
   * @param uuid    UUID of the online player.
   * @param address Address of the online player.
   */
  public void onPostLogin(final @NotNull UUID uuid, final @NotNull String address) {
    // Schedule a delayed task to whitelist the player.
    if (this.epicGuard.config().autoWhitelist().enabled()) {
      whitelistedPlayers.add(new UserInfo(uuid, address, System.currentTimeMillis() / 1000));
    }

    // Schedule a delayed task to check if the player has sent the Settings packet.
    if (this.epicGuard.config().settingsCheck().enabled()) {
      settingsPlayers.add(new UserInfo(uuid, address, System.currentTimeMillis() / 1000));
    }
  }

  private record UserInfo(UUID uuid, String address, long time) {
  }
}
