package com.averylg.marchmadness.http;

import com.averylg.marchmadness.MarchMadness;
import com.averylg.marchmadness.sockets.MCWebSocketClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.json.simple.parser.JSONParser;
import spark.Spark;

import java.util.List;

import static spark.Spark.before;
import static spark.Spark.options;

public class HttpHandler {

    private MCWebSocketClient client;
    private MarchMadness main;
    public HttpHandler(MarchMadness main, int port, MCWebSocketClient client) {
        this.main = main;
        this.client = client;
        Spark.port(port); // Choose an available port
        enableCORS("*", "*", "*");
        Spark.post("/minecwaft", (request, response) -> {
            // Handle the incoming POST request
            JsonElement jsonElement = JsonParser.parseString(request.body());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String teamA = jsonObject.get("teamA").getAsString();
            String teamB = jsonObject.get("teamB").getAsString();
            // Process the data as needed
            Bukkit.getScheduler().runTask(this.main, () -> {
                client.setVillagerPair(spawnVillagerPair(teamA, teamB));
            });
            return "Team A: " + teamA + " || Team B: " + teamB;
        });
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

    public Villager spawnVillager(String name, int x, int y, int z) {
        Villager viwwagew = (Villager) Bukkit.getWorld("world").spawnEntity(
                new Location(Bukkit.getWorld("world"), x, y, z),
                EntityType.VILLAGER
        );
        viwwagew.setCustomNameVisible(true);
        viwwagew.setCustomName(name);
        return viwwagew;
    }

    public List<Villager> spawnVillagerPair(String v1Name, String v2Name) {
        List<Villager> pair = client.getVillagerPair();
        pair.add(spawnVillager(v1Name, -37, 63, -35));
        pair.add(spawnVillager(v2Name, -39, 63, -35));
        return pair;
    }
}
