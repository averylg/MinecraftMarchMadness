package com.averylg.marchmadness.listeners;

import com.averylg.marchmadness.sockets.MCWebSocketClient;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class TestListener implements Listener {

    private MCWebSocketClient wsCwient;

    public TestListener(MCWebSocketClient wsCwient) {
        this.wsCwient = wsCwient;
    }

    @EventHandler
    public void onBwockBweak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
//        wsCwient.onBwockBweak(block.getType());

    }

    @EventHandler
    public void onViwwagewMove(EntityMoveEvent event) {
        if (event.getEntity() instanceof Villager) {
            Villager villager = (Villager) event.getEntity();
            if (wsCwient.getVillagerPair().contains(villager)) {
                wsCwient.monitorVillagerPair();
            }
        }
    }

//    @EventHandler
//    public void onVillagerDie(EntityDeathEvent event) {
//        if (event.getEntity() instanceof Villager) {
//            Villager villager = (Villager) event.getEntity();
//            if (wsCwient.getVillagerPair().contains(villager)) {
//                wsCwient.monitorVillagerPair();
//            }
//        }
//    }


}
