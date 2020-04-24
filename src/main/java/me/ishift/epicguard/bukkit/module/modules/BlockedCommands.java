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

package me.ishift.epicguard.bukkit.module.modules;

import me.ishift.epicguard.bukkit.module.Module;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.data.config.SpigotSettings;
import me.ishift.epicguard.common.util.MessageHelper;
import org.bukkit.entity.Player;

public class BlockedCommands implements Module {
    @Override
    public boolean execute(Player player, String command, String[] args) {
        if (SpigotSettings.blockedCommandsEnable && SpigotSettings.blockedCommands.contains(args[0])) {
            if (SpigotSettings.blockedCommandsBypass && player.hasPermission("epicguard.bypass.blocked-commands")) {
                return false;
            }
            player.sendMessage(MessageHelper.color(Messages.prefix + Messages.blockedCommand));
            return true;
        }
        return false;
    }
}