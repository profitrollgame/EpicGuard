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

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.standard.StringArgument;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.storage.AddressMeta;
import me.xneox.epicguard.core.util.TextUtils;
import net.kyori.adventure.audience.Audience;

import java.util.List;

public final class BlacklistCommand implements SubCommand {
    @Override
    public <A extends Audience> void register(CommandManager<A> commandManager, EpicGuard epicGuard) {
        final var removeArgument = StringArgument.<A>builder("subject")
                        .single()
                        .withDefaultDescription(ArgumentDescription.of("NickName or Address"))
                        .withSuggestionsProvider((ctx, st) -> {
                            if (!epicGuard.config().misc().disableIPTabCompletion()) {
                                return epicGuard.storageManager().viewAddresses(AddressMeta::blacklisted);
                            } else {
                                return List.of();
                            }
                        })
                        .build();
        final var addArgument = StringArgument.<A>builder("subject")
                        .single()
                        .withDefaultDescription(ArgumentDescription.of("NickName or Address"))
                        .build();
        commandManager.command(
                builder(commandManager)
                        .literal("blacklist")
                        .handler(ctx -> {
                            var config = epicGuard.messages().command();
                            ctx.getSender().sendMessage(TextUtils.component(config.prefix() + config.usage()
                                    .replace("{USAGE}", "/guard blacklist <add/remove> <nickname/address>")));
                        })
        );
        commandManager.command(
                builder(commandManager)
                        .literal("blacklist")
                        .literal("remove")
                        .argument(removeArgument)
                        .handler(ctx -> {
                            var config = epicGuard.messages().command();
                            var audience = ctx.getSender();
                            var argumentString = ctx.get(removeArgument);
                            var meta = epicGuard.storageManager().resolveAddressMeta(argumentString);
                            if (meta == null) {
                                audience.sendMessage(TextUtils.component(config.prefix() + config.invalidArgument()));
                                return;
                            }
                            if (!meta.blacklisted()) {
                                audience.sendMessage(TextUtils.component(config.prefix() + config.notBlacklisted().replace("{USER}", argumentString)));
                                return;
                            }
                            meta.blacklisted(false);
                            audience.sendMessage(TextUtils.component(config.prefix() + config.blacklistRemove().replace("{USER}", argumentString)));
                        })
        );

        commandManager.command(
                builder(commandManager)
                        .literal("blacklist")
                        .literal("add")
                        .argument(addArgument)
                        .handler(ctx -> {
                            var config = epicGuard.messages().command();
                            var audience = ctx.getSender();
                            var argumentString = ctx.get(addArgument);
                            var meta = epicGuard.storageManager().resolveAddressMeta(argumentString);
                            if (meta == null) {
                                audience.sendMessage(TextUtils.component(config.prefix() + config.invalidArgument()));
                                return;
                            }
                            if (meta.blacklisted()) {
                                audience.sendMessage(TextUtils.component(config.prefix() + config.alreadyBlacklisted().replace("{USER}", argumentString)));
                                return;
                            }

                            meta.blacklisted(true);
                            audience.sendMessage(TextUtils.component(config.prefix() + config.blacklistAdd().replace("{USER}", argumentString)));
                        })
        );
    }
}
