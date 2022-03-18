package com.neurotech.stressapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.data.ble.BleConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartFragment : Fragment(R.layout.fragment_start) {

    @Inject
    lateinit var bleConnection: BleConnection

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.application as App).component.inject(this)
        CoroutineScope(Dispatchers.Main).launch {
            startApplication()
        }
    }

    private suspend fun startApplication() {
        delay(1000)
        if (bleConnection.connectionState == BleConnection.CONNECTED) {
            findNavController().navigate(R.id.action_startFragment_to_mainHostFragment,
                bundleOf(),
                navOptions {
                    anim {
                        enter = R.anim.nav_default_enter_anim
                        popEnter = R.anim.nav_default_pop_enter_anim
                        exit = R.anim.nav_default_exit_anim
                        popExit = R.anim.nav_default_pop_exit_anim
                    }
                }
            )
        } else {
            findNavController().navigate(R.id.action_startFragment_to_searchFragment,
                bundleOf(),
                navOptions {
                    anim {
                        enter = R.anim.nav_default_enter_anim
                        popEnter = R.anim.nav_default_pop_enter_anim
                        exit = R.anim.nav_default_exit_anim
                        popExit = R.anim.nav_default_pop_exit_anim
                    }
                }
            )
        }
    }
}