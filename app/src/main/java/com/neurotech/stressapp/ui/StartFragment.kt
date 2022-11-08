package com.neurotech.stressapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.neurotech.domain.usecases.connection.GetConnectionState
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartFragment : Fragment(R.layout.fragment_start) {

    @Inject
    lateinit var connectionState: GetConnectionState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.application as App).component.inject(this)
        CoroutineScope(Dispatchers.Main).launch {
            startApplication()
        }
    }

    private suspend fun startApplication() {
        if (connectionState.invoke() == "CONNECTED") {
            findNavController().navigate(R.id.action_startFragment_to_mainHostFragment)
        } else {
            findNavController().navigate(R.id.action_startFragment_to_searchFragment)
        }
    }
}