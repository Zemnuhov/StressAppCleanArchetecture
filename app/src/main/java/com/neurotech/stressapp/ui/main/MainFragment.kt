package com.neurotech.stressapp.ui.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.neurotech.domain.BleConstant
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentMainBinding
import com.neurotech.stressapp.ui.main.Graph.PhaseGraphFragment
import com.neurotech.stressapp.ui.main.MainHost.MainFragmentViewModel
import com.neurotech.stressapp.ui.main.MainHost.MainFragmentViewModelFactory
import com.neurotech.stressapp.ui.main.PhaseItem.PhaseItemFragment
import com.neurotech.stressapp.ui.main.StatisticItem.StatisticItemFragment
import com.neurotech.stressapp.ui.main.TonicItem.TonicItemFragment
import javax.inject.Inject

class MainFragment: Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var factory: MainFragmentViewModelFactory
    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    val viewModel by lazy { ViewModelProvider(this, factory)[MainFragmentViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.application as App).component.inject(this)
        binding.imageView2.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_relaxFragment)
        }
        fillFragment()
        connectionObserver()

    }

    private fun connectionObserver(){
        viewModel.connectionState.observe(viewLifecycleOwner){
            if(it == BleConstant.CONNECTED){
                binding.disconnectView.visibility = View.GONE
            }else{
                binding.disconnectView.alpha = 0F
                binding.disconnectView.visibility = View.VISIBLE
                ObjectAnimator.ofFloat(binding.disconnectView, View.ALPHA,0F, 100F).apply {
                    duration = 10000
                    start()
                }
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}