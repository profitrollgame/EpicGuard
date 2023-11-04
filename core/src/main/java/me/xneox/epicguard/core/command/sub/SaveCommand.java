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

package me.xneox.epicguard.core.command.sub;

import cloud.commandframework.CommandManager;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.util.LogUtils;
import me.xneox.epicguard.core.util.TextUtils;
import net.kyori.adventure.audience.Audience;

import java.sql.SQLException;

public class SaveCommand implements SubCommand {
  @Override
  public <A extends Audience> void register(CommandManager<A> commandManager, EpicGuard epicGuard) {
    commandManager.command(
      builder(commandManager)
              .literal("save")
              .handler(ctx -> {
                try {
                  epicGuard.storageManager().database().save();
                  ctx.getSender().sendMessage(TextUtils.component(epicGuard.messages().command().prefix() + "<red>Data has been saved successfully."));
                } catch (SQLException ex) {
                  ctx.getSender().sendMessage(TextUtils.component(epicGuard.messages().command().prefix() +
                          "<red>An exception occurred when saving data. See console for details."));
                  LogUtils.catchException("Could not save data to the SQL database (command-induced)", ex);
                }
              })
    );
  }
}
