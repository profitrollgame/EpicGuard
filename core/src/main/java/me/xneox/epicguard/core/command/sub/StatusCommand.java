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
import me.xneox.epicguard.core.user.OnlineUser;
import me.xneox.epicguard.core.util.TextUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;

public final class StatusCommand implements SubCommand {
    @Override
    public <A extends Audience> void register(CommandManager<A> commandManager, EpicGuard epicGuard) {
        commandManager.command(
                builder(commandManager)
                        .literal("status")
                        .handler(ctx -> {
                            var config = epicGuard.messages().command();

                            ctx.getSender().get(Identity.UUID).ifPresent(uuid -> {
                                // UUID is present, enable notifications.
                                OnlineUser onlineUser = epicGuard.userManager().getOrCreate(uuid);
                                onlineUser.notifications(!onlineUser.notifications());
                                ctx.getSender().sendMessage(TextUtils.component(config.prefix() + config.toggleStatus()));
                            });
                        })
        );
    }
}
