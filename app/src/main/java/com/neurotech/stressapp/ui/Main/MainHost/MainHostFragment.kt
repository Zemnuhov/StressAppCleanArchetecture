package com.neurotech.stressapp.ui.Main.MainHost

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.databinding.FragmentHostBinding
import com.neurotech.stressapp.service.Service
import javax.inject.Inject

class MainHostFragment : Fragment(R.layout.fragment_host) {
    @Inject
    lateinit var factory: MainFragmentViewModelFactory
    @Inject
    lateinit var service: Service
    private var _binding: FragmentHostBinding? = null
    private val binding get() = _binding!!
    val viewModel by lazy { ViewModelProvider(this, factory)[MainFragmentViewModel::class.java] }

    private fun menuController(){
        activity?.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.findItem(R.id.menu_search).isVisible = false
                menu.findItem(R.id.disconnect_device).isVisible = true
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.disconnect_device ->{
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
                        true
                    }
                    R.id.recording -> {
                        //TODO(Удалить)
                        if (Singleton.recoding){
                            Singleton.stopRecoding()
                        }else{
                            Singleton.startRecoding()
                        }
                        true
                    }
                    else -> false
                }
            }

        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        _binding = FragmentHostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        menuController()
        service.bindService()
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}