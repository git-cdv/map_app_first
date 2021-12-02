package com.chkan.firstproject.features

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chkan.firstproject.R
import kotlinx.coroutines.*

class SplashFragment : Fragment() {

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onStart() {
        super.onStart()
        scope.launch {
            delay(1000L)
            withContext(Dispatchers.Main){findNavController().navigate(R.id.action_splashFragment_to_mainFragment)}
        }
    }
}

