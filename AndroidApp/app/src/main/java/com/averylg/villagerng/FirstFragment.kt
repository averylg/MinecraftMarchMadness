package com.averylg.villagerng

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.averylg.villagerng.databinding.FragmentFirstBinding
import com.averylg.villagerng.websockets.VillageRNGWebSocketClient
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URI

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val client = OkHttpClient()

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val serverURI = URI("ws://10.0.2.2:8081")
    private val webSocketClient = VillageRNGWebSocketClient(serverURI)

    private lateinit var villagerAInput: EditText
    private lateinit var villagerBInput: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webSocketClient.connect()

        villagerAInput = view.findViewById(R.id.villager_A_Text_input)
        villagerBInput = view.findViewById(R.id.villager_B_Text_input)

        binding.buttonAction.setOnClickListener {
            Log.d("VillageRNG", "FIRST BUTTON PRESSED MFS!")

            webSocketClient.send("AYO WE CHILLIN!")

            var vTextA = villagerAInput.text.toString()
            var vTextB = villagerBInput.text.toString()

//            webSocketClient.send("Villager A: $vTextA")
//            webSocketClient.send("Villager B: $vTextB")
            performHttpPostRequest(vTextA, vTextB)

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun performHttpPostRequest(villagerA: String, villagerB: String) {
        val url = "https://10.0.2.2:8084/minecraft"

        val requestBody = FormBody.Builder()
            .add("teamA", villagerA)
            .add("teamB", villagerB)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP_POST_REQUEST", "Failed to execute POST request: ${e.message}", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        Log.e("HTTP_POST_REQUEST", "HTTP error code: ${response.code}")
                    } else {
                        val responseBody = response.body?.string()
                        Log.d("HTTP_POST_REQUEST", "Response: $responseBody")
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webSocketClient.close()
        _binding = null
    }
}