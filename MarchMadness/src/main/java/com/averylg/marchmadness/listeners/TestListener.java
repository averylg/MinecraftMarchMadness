package com.averylg.marchmadness.listeners;

import com.averylg.marchmadness.sockets.MCWebSocketClient;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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
        wsCwient.onBwockBweak(block.getType());

    }
}
