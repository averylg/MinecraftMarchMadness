package com.averylg.marchmadness.listeners;

import com.averylg.marchmadness.sockets.MCWebSocketClient;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class TestListener implements Listener {

    private MCWebSocketClient wsClient;

    public TestListener(MCWebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    @EventHandler
    public void onVillagerExistBasically(EntityMoveEvent event) {
        if (event.getEntity() instanceof Villager) {
            Villager villager = (Villager) event.getEntity();
            if (wsClient.getVillagerPair().contains(villager)) {
                wsClient.monitorVillagerPair();
            }
        }
    }

    @EventHandler
    public void onVillagerUnalive(EntityDeathEvent event) {
        if (event.getEntity() instanceof Villager) {
            Villager villager = (Villager) event.getEntity();
            if (wsClient.getVillagerPair().contains(villager)) {
                for (int i = 0; i < wsClient.getZambiesA().size(); i++) {
                    wsClient.getZambiesA().get(i).setHealth(0);
                    wsClient.getZambiesB().get(i).setHealth(0);
                }
                for (Villager v : wsClient.getVillagerPair()) {
                    if (!v.isDead()) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendTitle("" + v.getCustomName() + " wins!", "");
                        }
                    }
                }
            }
        }
    }

}
