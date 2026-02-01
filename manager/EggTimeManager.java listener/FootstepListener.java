package me.koma.dragonegg.manager;

import java.util.HashMap;
import java.util.UUID;

public class EggTimeManager {

    private static final HashMap<UUID, Long> eggAcquireDay = new HashMap<>();

    public static void setEgg(UUID uuid, long day) {
        eggAcquireDay.put(uuid, day);
    }

    public static boolean isFootstepActive(UUID uuid, long currentDay) {
        if (!eggAcquireDay.containsKey(uuid)) return false;
        return currentDay - eggAcquireDay.get(uuid) < 5;
    }
}
long day = p.getWorld().getFullTime() / 24000;
EggTimeManager.setEgg(p.getUniqueId(), day);
package me.koma.dragonegg.listener;

import me.koma.dragonegg.manager.EggManager;
import me.koma.dragonegg.manager.EggTimeManager;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class FootstepListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!EggManager.eggHolders.contains(p.getUniqueId())) return;

        long time = p.getWorld().getTime();
        if (time > 12300) return; // 밤

        long day = p.getWorld().getFullTime() / 24000;
        if (!EggTimeManager.isFootstepActive(p.getUniqueId(), day)) return;

        Vector dir = e.getTo().toVector().subtract(e.getFrom().toVector()).normalize();

        for (int i = 0; i < 3; i++) {
            p.getWorld().spawnParticle(
                    Particle.BLOCK_CRACK,
                    p.getLocation().add(dir.clone().multiply(i * 0.3)),
                    2
            );
        }
    }
}
