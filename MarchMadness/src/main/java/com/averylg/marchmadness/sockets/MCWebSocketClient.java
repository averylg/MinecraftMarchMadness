package com.averylg.marchmadness.sockets;

import com.averylg.marchmadness.MarchMadness;
import com.averylg.marchmadness.listeners.TestListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MCWebSocketClient extends WebSocketClient {

    private MarchMadness main;
    public MCWebSocketClient(URI serverUri, MarchMadness main) {
        super(serverUri);
        this.main = main;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to WebSocket server");
        Bukkit.getPluginManager().registerEvents(new TestListener(this), this.main);
    }

    @Override
    public void onMessage(String s) {
        System.out.println("Received message from WebSocket server: " + s);
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

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Disconnected from WebSocket server");
    }

    @Override
    public void onError(Exception e) {
        System.err.println("WebSocket error: " + e.getMessage());
    }
}
