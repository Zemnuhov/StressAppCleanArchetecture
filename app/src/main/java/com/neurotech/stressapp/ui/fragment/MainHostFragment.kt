package com.neurotech.stressapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentHostBinding
import com.neurotech.stressapp.ui.MainActivity
import com.neurotech.stressapp.ui.viewmodel.MainFragmentViewModel

class MainHostFragment : Fragment(R.layout.fragment_host) {
    private var _binding: FragmentHostBinding? = null
    private val binding get() = _binding!!
    lateinit var menu: Menu
    private var _viewModel: MainFragmentViewModel? = null
    val viewModel get() = _viewModel!!

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
        _binding = FragmentHostBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        menuInit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}