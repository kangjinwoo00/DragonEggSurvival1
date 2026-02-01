package me.koma.dragonegg.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.entity.EnderPearl;

import java.util.Set;

public class ItemBanListener implements Listener {

    private final Set<Material> bannedItems = Set.of(
            Material.ELYTRA,
            Material.SHIELD,
            Material.ENDER_CHEST,
            Material.SHULKER_BOX,
            Material.TOTEM_OF_UNDYING,
            Material.TRIDENT
    );

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        if (bannedItems.contains(e.getItem().getType())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§c이 아이템은 사용이 금지되어 있습니다.");
        }
    }

    @EventHandler
    public void onPearl(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof EnderPearl) {
            e.setCancelled(true);
        }
    }
}
package me.koma.dragonegg.listener;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantListener implements Listener {

    @EventHandler
    public void onEnchant(EnchantItemEvent e) {
        e.getEnchantsToAdd().remove(Enchantment.MENDING);
        e.getEnchantsToAdd().remove(Enchantment.ARROW_INFINITE);

        e.getEnchantsToAdd().forEach((ench, level) -> {
            if (ench == Enchantment.DAMAGE_ALL && level > 10) {
                e.getEnchantsToAdd().put(ench, 10);
            }
            if (ench == Enchantment.PROTECTION_ENVIRONMENTAL && level > 7) {
                e.getEnchantsToAdd().put(ench, 7);
            }
        });
    }
}
