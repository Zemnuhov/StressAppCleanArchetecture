package com.neurotech.stressapp.ui.main.MainHost

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.cesarferreira.tempo.Tempo
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.databinding.FragmentHostBinding
import com.neurotech.stressapp.service.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainHostFragment : Fragment(R.layout.fragment_host) {
    @Inject
    lateinit var factory: MainFragmentViewModelFactory

    @Inject
    lateinit var service: Service
    private var _binding: FragmentHostBinding? = null
    private val binding get() = _binding!!
    val viewModel by lazy { ViewModelProvider(this, factory)[MainFragmentViewModel::class.java] }
    private var backPressedCount = 0
    private var lastBackPressedTime = 0L

    private fun menuController() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.findItem(R.id.menu_search).isVisible = false
                menu.findItem(R.id.menu_disconnect_device).isVisible = true
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_disconnect_device -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            viewModel.disconnectDevice()
                            delay(500)
                            findNavController().navigate(R.id.action_mainHostFragment_to_searchFragment)
                        }

                        true
                    }
                    R.id.recording -> {
                        //TODO(Удалить)
                        if (Singleton.recoding) {
                            Singleton.stopRecoding()
                        } else {
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
        navigationUpdate()
        service.bindService()
    }

    private fun navigationUpdate(){
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.bottomNavigationView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(
            requireActivity() as AppCompatActivity,
            navController,
            appBarConfiguration
        )
        requireActivity().onBackPressedDispatcher.addCallback {
            backPressedCount++
            if (Tempo.now.time - lastBackPressedTime < 2000) {
                if (backPressedCount == 2) {
                    requireActivity().finish()
                }
            } else {
                backPressedCount = 1
            }
            lastBackPressedTime = Tempo.now.time
            Toast.makeText(
                requireContext(),
                "Что бы выйти из приложения нажмите ещё раз",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onStop() {
        super.onStop()

    }

}