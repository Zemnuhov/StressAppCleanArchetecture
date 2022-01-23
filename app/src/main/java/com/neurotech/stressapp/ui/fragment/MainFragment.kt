package com.neurotech.stressapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neurotech.stressapp.R
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.ui.MainActivity
import com.neurotech.stressapp.ui.fragment.mainfragmentitem.PhaseGraphFragment
import com.neurotech.stressapp.ui.fragment.mainfragmentitem.TonicItemFragment
import com.neurotech.stressapp.ui.viewmodel.MainFragmentViewModel

class MainFragment: Fragment() {
    lateinit var mainView: View
    lateinit var viewModel: MainFragmentViewModel
    lateinit var menu: Menu

    private fun menuInit(){
        menu = (context as MainActivity).appMenu
        menu.findItem(R.id.menu_search).isVisible = false
        menu.findItem(R.id.disconnect_device).isVisible = true
        menu.findItem(R.id.disconnect_device).setOnMenuItemClickListener {
            viewModel.disconnectDevice()
            Singleton.showFragment(SearchFragment(),"connection")
            return@setOnMenuItemClickListener false
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainView = inflater.inflate(R.layout.main_fragment,container,false)
        viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        menuInit()
        fillFragment()
        return mainView
    }

    private fun fillFragment(){
        childFragmentManager.beginTransaction()
            .replace(R.id.graph_fragment_in_main, PhaseGraphFragment())
            .commit()
        childFragmentManager.beginTransaction()
            .replace(R.id.current_and_avg_layout, TonicItemFragment())
            .commit()
    }

}