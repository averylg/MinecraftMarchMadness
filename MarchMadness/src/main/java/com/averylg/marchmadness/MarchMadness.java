package com.averylg.marchmadness;

import com.averylg.marchmadness.listeners.TestListener;
import com.averylg.marchmadness.sockets.MCWebSocketClient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public final class MarchMadness extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("hehe zambie minceraft");
        String serverURI = "ws://localhost:8081";
        try {
            MCWebSocketClient cwient = new MCWebSocketClient(new URI(serverURI), this);
            cwient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
