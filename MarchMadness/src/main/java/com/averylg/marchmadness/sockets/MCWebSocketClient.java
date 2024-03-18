package com.averylg.marchmadness.sockets;

import com.averylg.marchmadness.MarchMadness;
import com.averylg.marchmadness.commands.TriggerCommands;
import com.averylg.marchmadness.listeners.TestListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class MCWebSocketClient extends WebSocketClient {

    private MarchMadness main;
    private List<Villager> villagerPair;
    public MCWebSocketClient(URI serverUri, MarchMadness main) {
        super(serverUri);
        this.main = main;
        this.villagerPair = new LinkedList<>();
    }

    public List<Villager> getVillagerPair() {
        return this.villagerPair;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        main.getLogger().info("Connected to WebSocket server");
        Bukkit.getPluginManager().registerEvents(new TestListener(this), this.main);
        main.getCommand("mm").setExecutor(new TriggerCommands(this));
    }

    @Override
    public void onMessage(String s) {
        main.getLogger().info("Received message from WebSocket server: " + s);
    }

    public void onBwockBweak(Material blockType) {
        main.getLogger().info("Thingy got called...");
        if (isOpen()) {
            String message = "Block broken: " + blockType.toString();

            send(message);
            main.getLogger().info("Thingy got sent! \\o/");
        } else {

            main.getLogger().warning("WS Connection not open, cannot send message");
        }
    }

    public void monitorVillagerPair() {
        if (isOpen()) {

            JSONObject overall = new JSONObject();
            JSONArray villagersJSON = new JSONArray();

            for (int i = 0; i < villagerPair.size(); i++) {
                Villager v = villagerPair.get(i);
                JSONObject vil = new JSONObject();
                vil.put("name", v.getCustomName());
                vil.put("health", v.getHealth());
                villagersJSON.add(vil);
            }
            overall.put("villagers", villagersJSON);
            String villJSONString = overall.toJSONString();
            send(villJSONString);
        } else {
            main.getLogger().warning("WS Connection not open, cannot monitor villagers");
        }
    }

    public void setVillagerPair(List<Villager> pair) {
        this.villagerPair = pair;
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        main.getLogger().info("Disconnected from WebSocket server");
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
