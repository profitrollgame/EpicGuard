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
import me.xneox.epicguard.core.storage.AddressMeta;
import me.xneox.epicguard.core.util.Constants;
import me.xneox.epicguard.core.util.TextUtils;
import net.kyori.adventure.audience.Audience;

public final class HelpCommand implements SubCommand {
    @Override
    public <A extends Audience> void register(CommandManager<A> commandManager, EpicGuard epicGuard) {
        commandManager.command(
                builder(commandManager)
                        .literal("help")
                        .handler(ctx -> {
                            for (String line : epicGuard.messages().command().mainCommand()) {
                                ctx.getSender().sendMessage(TextUtils.component(line
                                        .replace("{VERSION}", Constants.CURRENT_VERSION)
                                        .replace("{BLACKLISTED-IPS}", Integer.toString(epicGuard.storageManager().viewAddresses(AddressMeta::blacklisted).size()))
                                        .replace("{WHITELISTED-IPS}", Integer.toString(epicGuard.storageManager().viewAddresses(AddressMeta::whitelisted).size()))
                                        .replace("{CPS}", Integer.toString(epicGuard.attackManager().connectionCounter()))
                                        .replace("{ATTACK}", epicGuard.attackManager().isUnderAttack() ? "<green>✔" : "<red>✖")));
                            }
                        })
        );
    }
}
