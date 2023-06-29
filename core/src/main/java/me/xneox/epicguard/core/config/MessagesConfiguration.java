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

package me.xneox.epicguard.core.config;

import java.util.List;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@SuppressWarnings("ALL") // make intellij shut up about using final fields that would break the config loader.
@ConfigSerializable
public class MessagesConfiguration {
  @Comment("Messages Configuration Version")
  private int version = 1;

  // Config sections.
  private Command command = new Command();
  private Disconnect disconnect = new Disconnect();

  private String actionbarMonitor = "<red>EpicGuard <dark_gray>// <gold>%cps% <gray>connections/s <dark_gray>| %status%";
  private String actionbarNoAttack = "<gray>No attack...";
  private String actionbarAttack = "<red>Attack detected!";
  private String updateAvailable = "A new update is available: {NEWVER} (You are still on {OLDVER})";

  public Command command() {
    return this.command;
  }

  public Disconnect disconnect() {
    return this.disconnect;
  }

  public String actionbarMonitor() {
    return this.actionbarMonitor;
  }

  public String actionbarNoAttack() {
    return this.actionbarNoAttack;
  }

  public String actionbarAttack() {
    return this.actionbarAttack;
  }

  public String updateAvailable() {
    return this.updateAvailable;
  }

  @ConfigSerializable
  public static class Command {
    private String prefix = " <red>EpicGuard <dark_gray>// <gray>";
    private String usage = "<red>Correct usage: <gold>{USAGE}";
    private String unknownCommand = "<red>Unknown command, use <gold>/epicguard <red>for available commands.";
    private String whitelistAdd = "<gray>The user <green>{USER}</green> has been added to the whitelist.";
    private String whitelistRemove = "The user <gold>{USER} <gray>has been removed from the whitelist";
    private String blacklistAdd = "<gray>The user <red>{USER}</red> has been added to the blacklist.";
    private String blacklistRemove = "<gray>The user <gold>{USER}</gold> has been removed from the blacklist.";
    private String alreadyWhitelisted = "<red>The user <gold>{USER}</gold> is already whitelisted!";
    private String alreadyBlacklisted = "<red>The user <gold>{USER}</gold> >is already blacklisted!";
    private String notWhitelisted = "<red>The user <gold>{USER}</gold> is not in whitelist!";
    private String notBlacklisted = "<red>The user <gold>{USER}</gold> is not in the blacklist!";
    private String reloaded = "<gray>Succesfully reloaded config and messages!";
    private String toggleStatus = "<gray>You have toggled your attack status!";
    private String invalidArgument = "<red>Could not resolve address for this nickname, or provided address is invalid.";

    private List<String> mainCommand =
        List.of(
            "",
            " <gold>EpicGuard Protection System <dark_gray>- <gray>Running version <white>{VERSION}",
            "",
            " <dark_gray>▸ <gray>Under attack: {ATTACK}",
            " <dark_gray>▸ <gray>Connections: <yellow>{CPS}/s",
            " <dark_gray>▸ <gray>Blacklist: <yellow>{BLACKLISTED-IPS}</yellow> IPs",
            " <dark_gray>▸ <gray>Whitelist: <yellow>{WHITELISTED-IPS}</yellow> IPs",
            "",
            " <dark_gray>/<white>guard status </white>- <gray>Toggle attack status on actionbar.",
            " <dark_gray>/<white>guard reload </white>- <gray>Reload config and messages.",
            " <dark_gray>/<white>guard save </white>- <gray>Save data to the database.",
            " <dark_gray>/<white>guard analyze <nick/address> </white>- <gray>Perform detailed analysis on specified user.",
            " <dark_gray>/<white>guard whitelist <add/remove> <nick/address> </white>- <gray>Whitelist/unwhitelist an address or nickname.",
            " <dark_gray>/<white>guard blacklist <add/remove> <nick/address> </white>- <gray>Blacklist/unblacklist an address or nickname.",
            "");

    private List<String> analyzeCommand =
        List.of(
            "",
            " <gold>EpicGuard Analysis System <dark_gray>- <gray>Results for <white>{ADDRESS}",
            "",
            " <yellow>Geographic Data:",
            "  <dark_gray>▸ <gray>Country: <white>{COUNTRY}",
            "  <dark_gray>▸ <gray>City: <white>{CITY}",
            "",
            " <yellow>Known Accounts<gold> ({ACCOUNT-AMOUNT}):",
            "  <dark_gray>▸ <white>{NICKNAMES}",
            "",
            " <yellow>Other Data:",
            "  <dark_gray>▸ <gray>Whitelisted: {WHITELISTED}",
            "  <dark_gray>▸ <gray>Blacklisted: {BLACKLISTED}",
            "");

    public String prefix() {
      return this.prefix;
    }

    public String usage() {
      return this.usage;
    }

    public String unknownCommand() {
      return this.unknownCommand;
    }

    public String whitelistAdd() {
      return this.whitelistAdd;
    }

    public String whitelistRemove() {
      return this.whitelistRemove;
    }

    public String blacklistAdd() {
      return this.blacklistAdd;
    }

    public String blacklistRemove() {
      return this.blacklistRemove;
    }

    public String alreadyWhitelisted() {
      return this.alreadyWhitelisted;
    }

    public String alreadyBlacklisted() {
      return this.alreadyBlacklisted;
    }

    public String notWhitelisted() {
      return this.notWhitelisted;
    }

    public String notBlacklisted() {
      return this.notBlacklisted;
    }

    public String reloaded() {
      return this.reloaded;
    }

    public String toggleStatus() {
      return this.toggleStatus;
    }

    public String invalidArgument() {
      return this.invalidArgument;
    }

    public List<String> mainCommand() {
      return this.mainCommand;
    }

    public List<String> analyzeCommand() {
      return this.analyzeCommand;
    }
  }

  @ConfigSerializable
  public static class Disconnect {
    private List<String> geographical = List.of(
        "<dark_gray>» <gray>You have been kicked by <aqua>AntiBot Protection</aqua>:",
        "<dark_gray>» <red>Your country/city is not allowed on this server.");

    private List<String> blacklisted = List.of(
        "<dark_gray>» <gray>You have been kicked by <aqua>AntiBot Protection</aqua>:",
        "<dark_gray>» <red>You have been blacklisted on this server.");

    private List<String> attackLockdown = List.of(
        "<dark_gray>» <gray>You have been kicked by <aqua>AntiBot Protection</aqua>:",
        "<dark_gray>» <red>Server is under attack, please wait some seconds before joining.");

    private List<String> proxy = List.of(
        "<dark_gray>» <gray>You have been kicked by <aqua>AntiBot Protection</aqua>:",
        "<dark_gray>» <red>You are using VPN or Proxy.");

    private List<String> reconnect = List.of(
        "<dark_gray>» <gray>You have been kicked by <aqua>AntiBot Protection</aqua>:",
        "<dark_gray>» <red>Join the server again.");

    private List<String> nickname = List.of(
        "<dark_gray>» <gray>You have been kicked by <aqua>AntiBot Protection</aqua>:",
        "<dark_gray>» <red>You nickname is not allowed on this server.");

    private List<String> accountLimit = List.of(
        "<dark_gray>» <gray>You have been kicked by <aqua>AntiBot Protection</aqua>:",
        "<dark_gray>» <red>You have too many accounts on your IP address.");

    private List<String> serverListPing = List.of(
        "<dark_gray>» <gray>You have been kicked by <aqua>AntiBot Protection</aqua>:",
        "<dark_gray>» <red>You must add our server to your servers list to verify yourself.");

    private List<String> nameSimilarity = List.of(
        "<dark_gray>» <gray>You have been kicked by <aqua>AntiBot Protection</aqua>:",
        "<dark_gray>» <red>Your nickname is too similar to other users connecting to the server.");

    private List<String> settingsPacket = List.of(
        "<dark_gray>» <gray>You have been kicked by <aqua>AntiBot Protection</aqua>:",
        "<dark_gray>» <red>Bot-like behaviour detected, please join the server again.");

    public List<String> geographical() {
      return this.geographical;
    }

    public List<String> blacklisted() {
      return this.blacklisted;
    }

    public List<String> attackLockdown() {
      return this.attackLockdown;
    }

    public List<String> proxy() {
      return this.proxy;
    }

    public List<String> reconnect() {
      return this.reconnect;
    }

    public List<String> nickname() {
      return this.nickname;
    }

    public List<String> accountLimit() {
      return this.accountLimit;
    }

    public List<String> serverListPing() {
      return this.serverListPing;
    }

    public List<String> nameSimilarity() {
      return this.nameSimilarity;
    }

    public List<String> settingsPacket() {
      return this.settingsPacket;
    }
  }
}
