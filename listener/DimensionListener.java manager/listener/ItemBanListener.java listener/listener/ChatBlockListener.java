package me.koma.dragonegg.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class ChatBlockListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent e) {
        e.message(null);
    }
}
e.setDeathMessage(null);
package me.koma.dragonegg.listener;

import me.koma.dragonegg.manager.EggManager;
import org.bukkit.*;
import org.bukkit.block.Beacon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!EggManager.eggHolders.remove(e.getEntity().getUniqueId())) return;

        Location l = e.getEntity().getLocation();

        l.getBlock().setType(Material.OBSIDIAN);
        l.clone().add(0, 1, 0).getBlock().setType(Material.DRAGON_EGG);
        l.clone().add(0, 2, 0).getBlock().setType(Material.BEACON);

        Beacon beacon = (Beacon) l.clone().add(0, 2, 0).getBlock().getState();
        beacon.setPrimaryEffect(PotionEffectType.REGENERATION);
        beacon.update();

        new BukkitRunnable() {
            @Override
            public void run() {
                l.clone().add(0, 2, 0).getBlock().setType(Material.AIR);
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("DragonEggSurvival"), 20L * 60 * 100);
    }
}
