package com.averylg.marchmadness.sockets;

import com.averylg.marchmadness.MarchMadness;
import com.averylg.marchmadness.commands.TriggerCommands;
import com.averylg.marchmadness.listeners.TestListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MCWebSocketClient extends WebSocketClient {

    private MarchMadness main;
    private List<Villager> villagerPair;


    private List<Zombie> zambiesA;
    private List<Zombie> zambiesB;
    public MCWebSocketClient(URI serverUri, MarchMadness main) {
        super(serverUri);
        this.main = main;
        this.villagerPair = new LinkedList<>();
        zambiesA = new ArrayList<>();
        zambiesB = new ArrayList<>();
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

    public void monitorVillagerPair() {
        if (isOpen()) {

            JSONObject overall = new JSONObject();

            Villager v1 = villagerPair.get(0);
            JSONObject v1JSON = new JSONObject();
            v1JSON.put("name", v1.getCustomName());
            v1JSON.put("health", v1.getHealth());
            Villager v2 = villagerPair.get(1);
            JSONObject v2JSON = new JSONObject();
            v2JSON.put("name", v2.getCustomName());
            v2JSON.put("health", v2.getHealth());

            overall.put("villager1", v1JSON);
            overall.put("villager2", v2JSON);

            if (v1.getHealth() <= 0 && v2.getHealth() <= 0) {
                overall.put("winner", "It's a draw :( make them go again!");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendTitle("Issa draw", "Let's try this again...");
                }
            } else if (v1.getHealth() <= 0) {
                v2.setInvulnerable(true);
                overall.put("winner", v2.getCustomName() + " wins!");

            } else if (v2.getHealth() <= 0) {
                v1.setInvulnerable(true);
                overall.put("winner", v1.getCustomName() + " wins!");

            } else {
                overall.put("winner", "Round still in progress...");
            }

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

    public List<Zombie> spawnZambies(Location loc) {
        List<Zombie> zambies = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // I refuse to change this spelling
            Zombie zambie = (Zombie) Bukkit.getWorld("world").spawnEntity(loc, EntityType.ZOMBIE);
            zambie.setBaby(false);
            zambie.getEquipment().clear();
            zambies.add(zambie);
        }
        return zambies;
    }

    public List<Zombie> getZambiesA() {
        return zambiesA;
    }

    public List<Zombie> getZambiesB() {
        return zambiesB;
    }
}
