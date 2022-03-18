package com.neurotech.stressapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.neurotech.stressapp.R
import com.neurotech.stressapp.data.ble.BleConnection
import com.neurotech.stressapp.databinding.FragmentSearchBinding
import com.neurotech.stressapp.ui.MainActivity
import com.neurotech.stressapp.ui.adapter.SearchCardAdapter
import com.neurotech.stressapp.ui.viewmodel.SearchFragmentViewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    companion object {
        lateinit var viewModel: SearchFragmentViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[SearchFragmentViewModel::class.java]
        initView()
        setObservers()
        setListeners()
    }

    private fun initView() {
        (context as MainActivity).appMenu.findItem(R.id.menu_search).isVisible = true
        (context as MainActivity).appMenu.findItem(R.id.disconnect_device).isVisible = false
        binding.recyclerViewList.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewList.adapter = SearchCardAdapter(listOf())
        binding.connectProgress.visibility = View.GONE
    }

    private fun setListeners() {
        binding.refreshListDevice.setOnRefreshListener {
            viewModel.searchDevice()
        }
        (context as MainActivity).appMenu.findItem(R.id.menu_search).setOnMenuItemClickListener {
            viewModel.searchDevice()
            return@setOnMenuItemClickListener false
        }
    }

    private fun setObservers() {
        viewModel.searchState.observe(viewLifecycleOwner) {
            binding.refreshListDevice.isRefreshing = it
        }
        viewModel.deviceList.observe(viewLifecycleOwner) {
            binding.recyclerViewList.adapter = SearchCardAdapter(it)
        }
        viewModel.deviceState.observe(viewLifecycleOwner) {
            Log.e("DS", it.toString())
            when (it) {
                BleConnection.CONNECTING -> binding.connectProgress.visibility = View.VISIBLE
                BleConnection.CONNECTED -> {
                    binding.connectProgress.visibility = View.GONE
                    findNavController().navigate(R.id.action_searchFragment_to_mainHostFragment,
                        bundleOf(),
                        navOptions {
                            this.anim {
                                enter = R.anim.nav_default_enter_anim
                                popEnter = R.anim.nav_default_pop_enter_anim
                                exit = R.anim.nav_default_exit_anim
                                popExit = R.anim.nav_default_pop_exit_anim
                            }
                        })
                }
                else -> binding.connectProgress.visibility = View.GONE
            }
        }
    }
}
