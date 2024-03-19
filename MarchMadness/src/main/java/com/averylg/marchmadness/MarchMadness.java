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
        getLogger().info("hehe zambie minceraft");
        String serverURI = "ws://localhost:8081";


        try {
            MCWebSocketClient cwient = new MCWebSocketClient(new URI(serverURI), this);
            cwient.connect();
            getLogger().info("reeeeeeeeeeeeeeeeeee");
            // Set up your HTTP server
            new HttpHandler(this, 8084, cwient);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
