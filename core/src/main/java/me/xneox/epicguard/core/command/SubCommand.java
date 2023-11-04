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

package me.xneox.epicguard.core.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import me.xneox.epicguard.core.EpicGuard;
import net.kyori.adventure.audience.Audience;

/**
 * A subcommand of the /epicguard command.
 */
public interface SubCommand {
  <A extends Audience> void register(CommandManager<A> commandManager, EpicGuard epicGuard);

  default <A extends Audience> Command.Builder<A> builder(CommandManager<A> commandManager) {
    return commandManager.commandBuilder("epicguard").permission("epicguard.admin");
  }
}
