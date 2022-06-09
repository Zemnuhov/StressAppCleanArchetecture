package com.neurotech.stressapp.ui.Main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.neurotech.stressapp.R
import com.neurotech.stressapp.ui.Main.Graph.PhaseGraphFragment
import com.neurotech.stressapp.ui.Main.PhaseItem.PhaseItemFragment
import com.neurotech.stressapp.ui.Main.StatisticItem.StatisticItemFragment
import com.neurotech.stressapp.ui.Main.TonicItem.TonicItemFragment

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