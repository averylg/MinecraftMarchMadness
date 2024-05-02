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
import java.net.URI

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

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

            webSocketClient.send("Villager A: $vTextA")
            webSocketClient.send("Villager B: $vTextB")

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webSocketClient.close()
        _binding = null
    }
}