package com.neurotech.stressapp.ui.Main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentMainBinding
import com.neurotech.stressapp.ui.Main.Graph.PhaseGraphFragment
import com.neurotech.stressapp.ui.Main.PhaseItem.PhaseItemFragment
import com.neurotech.stressapp.ui.Main.StatisticItem.StatisticItemFragment
import com.neurotech.stressapp.ui.Main.TonicItem.TonicItemFragment

class MainFragment: Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imageView2.setOnClickListener {
            findNavController().navigate(
                R.id.action_mainFragment_to_relaxFragment,
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

    override fun onDestroy() {
        super.onDestroy()
    }
}