package me.koma.dragonegg.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandBlockListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().toLowerCase();

        if (cmd.contains("tp") || cmd.contains("whereami") || cmd.contains("locate")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§c좌표 관련 명령어는 사용할 수 없습니다.");
        }
    }
}
package me.koma.dragonegg.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EggPlaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType() != Material.DRAGON_EGG) return;

        var loc = e.getBlockPlaced().getLocation();

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 20 * 60 * 10) { // 10분 지속
                    cancel();
                    return;
                }

                loc.getWorld().playSound(
                        loc,
                        Sound.BLOCK_BEACON_AMBIENT,
                        1f,
                        1f
                );
                ticks += 100;
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("DragonEggSurvival"), 0, 100);
    }
}
package me.koma.dragonegg.listener;

import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DragonXPListener implements Listener {

    @EventHandler
    public void onDragonDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderDragon) {
            e.setDroppedExp(8000);
        }
    }
}
