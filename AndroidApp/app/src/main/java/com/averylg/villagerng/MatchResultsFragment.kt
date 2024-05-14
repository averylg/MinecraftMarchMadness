package com.averylg.villagerng

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.averylg.villagerng.databinding.FragmentMatchResultsBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject

class MatchResultsFragment : Fragment() {

    private var _binding: FragmentMatchResultsBinding? = null
    private val binding get() = _binding!!


    private lateinit var okSocketClient: OkHttpClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMatchResultsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        okSocketClient = OkHttpClient()

        val request = Request.Builder()
            .url("ws://10.0.2.2:8081")
            .build()

        val webSocketListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                val winnerLabel = binding.winner
                val score = binding.score

                val msg = JSONObject(bytes.utf8())
                val winnerText = msg.getString("winner")

                val villager1 = msg.getJSONObject("villager1")
                val villager2 = msg.getJSONObject("villager2")



                if (winnerText.contains("wins")) {
                    if (villager1.getInt("health") == 0) {
                        score.text = "Health Remaining: ${villager2.get("health")}"
                    } else if (villager2.getInt("health") == 0) {
                        score.text = "Health Remaining: ${villager1.get("health")}"
                    }
                    winnerLabel.text = msg.getString("winner")
                    binding.buttonRestart.setOnClickListener {
                        findNavController().navigate(R.id.action_matchResultsFragment_to_FirstFragment)
                    }
                } else {
                    winnerLabel.text = "It's a draw!"
                    score.text = "Tap the button to rerun the match!"
                    binding.buttonRestart.setOnClickListener {



                        findNavController().navigate(R.id.action_matchResultsFragment_to_SecondFragment)
                    }
                }
            }
        }
        okSocketClient.newWebSocket(request, webSocketListener)
    }
}