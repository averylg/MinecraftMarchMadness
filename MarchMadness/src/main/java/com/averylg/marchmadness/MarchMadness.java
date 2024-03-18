package com.averylg.marchmadness;

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
        // Set up your HTTP server

        Spark.port(8084); // Choose an available port
        enableCORS("*", "*", "*");
        Spark.post("/minecwaft", (request, response) -> {
            // Handle the incoming POST request
            String dataFromNode = request.body(); // Get the data sent from Node.js
            // Process the data as needed
            return "Received data from Node.js: " + dataFromNode;
        });
        try {
            MCWebSocketClient cwient = new MCWebSocketClient(new URI(serverURI), this);
            cwient.connect();
            getLogger().info("reeeeeeeeeeeeeeeeeee");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // Thank you Copilot, great oracle of the sand \o/
    private static void enableCORS(final String origin, final String methods, final String headers) {
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);

            response.type("application/json");
        });
    }
}
