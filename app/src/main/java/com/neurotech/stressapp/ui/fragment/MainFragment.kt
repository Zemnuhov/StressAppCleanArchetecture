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




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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