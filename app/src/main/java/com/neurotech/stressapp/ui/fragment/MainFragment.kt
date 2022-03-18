package com.neurotech.stressapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.neurotech.stressapp.R
import com.neurotech.stressapp.ui.MainActivity
import com.neurotech.stressapp.ui.fragment.mainfragmentitem.PhaseGraphFragment
import com.neurotech.stressapp.ui.fragment.mainfragmentitem.PhaseItemFragment
import com.neurotech.stressapp.ui.fragment.mainfragmentitem.StatisticItemFragment
import com.neurotech.stressapp.ui.fragment.mainfragmentitem.TonicItemFragment
import com.neurotech.stressapp.ui.viewmodel.MainFragmentViewModel

class MainFragment: Fragment(R.layout.fragment_main) {

    lateinit var viewModel: MainFragmentViewModel
    lateinit var menu: Menu

    private fun menuInit(){
        menu = (context as MainActivity).appMenu
        menu.findItem(R.id.menu_search).isVisible = false
        menu.findItem(R.id.disconnect_device).isVisible = true
        menu.findItem(R.id.disconnect_device).setOnMenuItemClickListener {
            viewModel.disconnectDevice()
            findNavController().navigate(R.id.action_mainHostFragment_to_searchFragment,
                bundleOf(),
                navOptions {
                    this.anim {
                        enter = R.anim.nav_default_enter_anim
                        popEnter = R.anim.nav_default_pop_enter_anim
                        exit = R.anim.nav_default_exit_anim
                        popExit = R.anim.nav_default_pop_exit_anim
                    }
                })
            return@setOnMenuItemClickListener false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        menuInit()
        return inflater.inflate(R.layout.fragment_main,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        fillFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun fillFragment(){
        childFragmentManager.beginTransaction()
            .replace(R.id.graph_fragment_in_main, PhaseGraphFragment())
            .commit()
        childFragmentManager.beginTransaction()
            .replace(R.id.current_and_avg_layout, TonicItemFragment())
            .commit()
        childFragmentManager.beginTransaction()
            .replace(R.id.peaks_layout, PhaseItemFragment())
            .commit()
        childFragmentManager.beginTransaction()
            .replace(R.id.statistic_layout, StatisticItemFragment())
            .commit()
    }

}