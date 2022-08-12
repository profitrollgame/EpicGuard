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

package me.xneox.epicguard.paper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;

public class PaperCommandHandler extends CommandHandler implements CommandExecutor, Listener {
  public PaperCommandHandler(EpicGuard epicGuard) {
    super(epicGuard);
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    this.handleCommand(args, sender);
    return true;
  }

  private static final String[] EMPTY_ARRAY = new String[0];

  @EventHandler
  public void onTabComplete(AsyncTabCompleteEvent event) {
    if (!event.isCommand()) {
      return;
    }

    final String buffer = event.getBuffer();
    final String input = buffer.startsWith("/") ? buffer.substring(1) : buffer;
    final String[] tokens = input.split(" ");
    final String command = tokens[0].toLowerCase(Locale.ROOT);

    if (command.equals("guard") || command.equals("epicguardpaper") || command.equals("guardpaper")) {
      if (tokens.length == 0) {
        event.setCompletions(new ArrayList<>(this.handleSuggestions(EMPTY_ARRAY)));
      }

      event.setCompletions(new ArrayList<>(this.handleSuggestions(Arrays.copyOfRange(tokens, 1, tokens.length))));
    }
  }
}
