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
import cloud.commandframework.arguments.standard.StringArgument;
import com.google.common.net.InetAddresses;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.util.TextUtils;
import net.kyori.adventure.audience.Audience;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AnalyzeCommand implements SubCommand {

    @Override
    public <A extends Audience> void register(CommandManager<A> commandManager, EpicGuard epicGuard) {
        final StringArgument<A> addressArgument = StringArgument.<A>builder("address")
                .single()
                .withSuggestionsProvider((ctx, a) -> {
                    if (epicGuard.config().misc().disableIPTabCompletion()) {
                        return Collections.emptyList();
                    }

                    final List<String> addresses = new ArrayList<>();
                    epicGuard.storageManager().addresses().asMap().forEach((k, v) -> addresses.add(k));
                    return addresses;
                })
                .build();
        commandManager.command(
                builder(commandManager)
                        .literal("analyze")
                        .argument(addressArgument)
                        .handler(ctx -> {
                            final var config = epicGuard.messages().command();
                            final var audience = ctx.getSender();
                            var optionalAddress = ctx.getOptional(addressArgument);
                            if (optionalAddress.isEmpty()) {
                                audience.sendMessage(TextUtils.component(config.prefix() +
                                        config.usage().replace("{USAGE}", "/guard analyze <nickname/address>")));
                                return;
                            }
                            // Assume that executor provided an address as the argument.
                            var address = optionalAddress.get();

                            var meta = epicGuard.storageManager().resolveAddressMeta(address);
                            if (meta == null) {
                                ctx.getSender().sendMessage(TextUtils.component(config.prefix() + config.invalidArgument()));
                                return;
                            }

                            // If executor provided nickname as the argument instead, we have to find their IP address.
                            if (!InetAddresses.isInetAddress(address)) {
                                for (var entry : epicGuard.storageManager().addresses().asMap().entrySet()) {
                                    if (entry.getValue().equals(meta)) {
                                        address = entry.getKey();
                                        break;
                                    }
                                }
                            }

                            for (final String line : config.analyzeCommand()) {
                                audience.sendMessage(TextUtils.component(line
                                        .replace("{ADDRESS}", address)
                                        .replace("{COUNTRY}", epicGuard.geoManager().countryCode(address))
                                        .replace("{CITY}", epicGuard.geoManager().city(address))
                                        .replace("{WHITELISTED}", meta.whitelisted() ? "<green>✔" : "<red>✖")
                                        .replace("{BLACKLISTED}", meta.blacklisted() ? "<green>✔" : "<red>✖")
                                        .replace("{ACCOUNT-AMOUNT}", Integer.toString(meta.nicknames().size()))
                                        .replace("{NICKNAMES}", String.join(", ", meta.nicknames()))));
                            }
                        })
        );

    }
}
