package me.ishift.epicguard.bukkit.listener.server;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;
import me.ishift.epicguard.universal.types.AttackType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        SpeedCheck.increase(AttackType.PING);
        Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> SpeedCheck.decrease(AttackType.PING), 20L);

        if (SpeedCheck.getPingPerSecond() > Config.pingSpeed) {
            if (Config.bandwidthOptimizer) {
                event.setMotd("");
                event.setMaxPlayers(0);
            }
        }
    }
}
