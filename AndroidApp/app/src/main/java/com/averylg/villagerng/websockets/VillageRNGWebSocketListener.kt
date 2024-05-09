package com.averylg.villagerng.websockets

import android.util.Log
import android.widget.TextView
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class VillageRNGWebSocketListener : WebSocketListener() {

    private lateinit var textViewVillagerA: TextView
    private lateinit var textViewVillagerB: TextView
    private lateinit var textViewHealthA: TextView
    private lateinit var textViewHealthB: TextView

    init {

    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        webSocket.send("REEEEEEEEEEEEEEEE")
        Log.d("AIGHT", "connected")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("AYOOOO", text)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(100, null)
        Log.d("Closing", reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("Failure", t.message.toString())
    }

}