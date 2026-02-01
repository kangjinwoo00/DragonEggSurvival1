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
