package com.neurotech.stressapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.stressapp.R
import com.neurotech.stressapp.Singleton
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

        viewModel.tonicValue.observe(viewLifecycleOwner){
            Log.e("Data", it.toString())
        }
        return mainView
    }


}