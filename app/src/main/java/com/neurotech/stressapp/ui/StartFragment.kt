package com.neurotech.stressapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.neurotech.domain.usecases.connection.ConnectionToPeripheral
import com.neurotech.domain.usecases.connection.GetConnectionState
import com.neurotech.domain.usecases.settings.GetDeviceInMemory
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.di.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartFragment : Fragment(R.layout.fragment_start) {

    @Inject
    lateinit var connectionState: GetConnectionState

    @Inject
    lateinit var device: GetDeviceInMemory

    @Inject
    lateinit var connectionToPeripheral: ConnectionToPeripheral

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getAppComponent().inject(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        CoroutineScope(Dispatchers.Main).launch {
            if (connectionState.invoke() == "CONNECTED") {
                findNavController().navigate(R.id.action_startFragment_to_mainHostFragment)
            } else {
                val connectDevice = device.invoke()
                if(connectDevice != null){
                    connectionToPeripheral.invoke(connectDevice)
                    findNavController().navigate(R.id.action_startFragment_to_mainHostFragment)
                }else{
                    findNavController().navigate(R.id.action_startFragment_to_searchFragment)
                }
            }
        }
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    private fun Fragment.getAppComponent(): AppComponent{
        return (requireActivity().application as App).component
    }
}