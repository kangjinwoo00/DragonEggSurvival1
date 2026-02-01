package me.koma.dragonegg.manager;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnderEffectManager extends BukkitRunnable {

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!EggManager.eggHolders.contains(p.getUniqueId())) continue;

            p.getWorld().spawnParticle(
                    Particle.PORTAL,
                    p.getLocation(),
                    20,
                    0.5, 1, 0.5
            );

            p.getWorld().playSound(
                    p.getLocation(),
                    Sound.ENTITY_ENDERMAN_AMBIENT,
                    0.3f,
                    1f
            );
        }
    }
}
new EnderEffectManager().runTaskTimer(this, 0, 20 * 10);
package me.koma.dragonegg.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.PlayerInventory;

public class PotionLimitListener implements Listener {

    @EventHandler
    public void onPotionMove(InventoryClickEvent e) {
        if (!(e.getWhoClicked().getInventory() instanceof PlayerInventory inv)) return;

        int potions = 0;
        for (var item : inv.getContents()) {
            if (item != null && item.getType() == Material.POTION) {
                potions++;
            }
        }

        if (potions > 2) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage("§c포션은 최대 2개까지만 소지할 수 있습니다.");
        }
    }
}
package me.koma.dragonegg.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GoldenAppleListener implements Listener {

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        if (e.getItem().getType().toString().contains("GOLDEN_APPLE")) {
            e.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
            e.getPlayer().addPotionEffect(
                    new PotionEffect(PotionEffectType.REGENERATION, 40, 1)
            );
        }
    }
}
package me.koma.dragonegg.manager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class WorldBorderManager {

    public static void apply() {
        for (World w : Bukkit.getWorlds()) {
            WorldBorder border = w.getWorldBorder();
            border.setCenter(0, 0);

            switch (w.getEnvironment()) {
                case NORMAL -> border.setSize(1000);
                case NETHER -> border.setSize(500);
                case THE_END -> border.setSize(400);
            }
        }
    }
}
WorldBorderManager.apply();
package me.koma.dragonegg.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EndBlockListener implements Listener {

    @EventHandler
    public void onEndTeleport(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_GATEWAY) {
            e.setCancelled(true);
        }
    }
}
