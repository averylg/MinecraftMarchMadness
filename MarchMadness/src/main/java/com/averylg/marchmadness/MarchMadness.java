package com.averylg.marchmadness;

import com.averylg.marchmadness.http.HttpHandler;
import com.averylg.marchmadness.sockets.MCWebSocketClient;
import org.bukkit.plugin.java.JavaPlugin;
import spark.Spark;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.before;
import static spark.Spark.options;

public final class MarchMadness extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("March Madness Plugin Started");
        String serverURI = "ws://localhost:8081";

        try {
            MCWebSocketClient client = new MCWebSocketClient(new URI(serverURI), this);
            client.connect();
            // Set up your HTTP server
            new HttpHandler(this, 8084, client);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
