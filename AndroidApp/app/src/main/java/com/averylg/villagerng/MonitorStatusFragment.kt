package com.averylg.villagerng

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.averylg.villagerng.databinding.FragmentSecondBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MonitorStatusFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

//    private val serverURI = URI("ws://127.0.0.1:8081")
//    private val webSocketClient = VillageRNGWebSocketClient(serverURI)


    private lateinit var okSocketClient: OkHttpClient

    private lateinit var villagerNameA: TextView
    private lateinit var villagerNameB: TextView
    private lateinit var villagerHealthA: TextView
    private lateinit var villagerHealthB: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)?.supportActionBar?.title = "Monitor Status"

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        okSocketClient = OkHttpClient()

        val request = Request.Builder()
            .url("ws://10.0.2.2:8081")
            .build()

        villagerNameA = view.findViewById<TextView>(R.id.villager_A_Name)
        villagerNameB = view.findViewById<TextView>(R.id.villager_B_Name)
        villagerHealthA = view.findViewById<TextView>(R.id.villager_A_Health)
        villagerHealthB = view.findViewById<TextView>(R.id.villager_B_health)

        val webSocketListener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("VillageRNG", "Web Socket Opened")
            }



            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("VillageRNG Failure", response.toString(), t)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("VillageRNG", "Closed all dat")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("VillageRNG", "ClosING all dat")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {


                try {
                    val jsonObject = JSONObject(bytes.utf8())

                    // Extract data from JSON object
                    val teamA = jsonObject.getJSONObject("villager1")
                    val teamB = jsonObject.getJSONObject("villager2")

                    val nameA = teamA.getString("name")
                    val healthA = teamA.getInt("health")
                    val nameB = teamB.getString("name")
                    val healthB = teamB.getInt("health")

                    // Update UI elements on the main thread
                    activity?.runOnUiThread {
                        villagerNameA.text = nameA
                        villagerHealthA.text = "Health: $healthA"

                        villagerNameB.text = nameB
                        villagerHealthB.text = "Health: $healthB"

                    }

                    val result = jsonObject.getString("winner")

                    if (result.contains("wins") || result.contains("draw")) {
                        findNavController().navigate(R.id.action_SecondFragment_to_matchResultsFragment)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        okSocketClient.newWebSocket(request, webSocketListener)


        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}