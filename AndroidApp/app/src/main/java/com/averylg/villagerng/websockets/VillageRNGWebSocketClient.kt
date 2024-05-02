package com.averylg.villagerng.websockets

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class VillageRNGWebSocketClient(serverUri: URI) : WebSocketClient(serverUri) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        println("Android WebSocket connection opened")
        // Perform actions when connection is established
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("WebSocket connection closed")
        // Handle closure of the WebSocket connection
    }

    override fun onMessage(message: String?) {
        println("Received message: $message")
        // Process incoming messages from the WebSocket server
    }

    override fun onError(ex: Exception?) {
        println("WebSocket error occurred: ${ex?.message}")
        // Handle WebSocket errors
    }
}
