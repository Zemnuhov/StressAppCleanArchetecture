package com.neurotech.stressapp.ui.fragment.mainfragmentitem

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.neurotech.stressapp.R
import com.neurotech.stressapp.data.database.entity.ResultSourceCounterItem
import com.neurotech.stressapp.databinding.ItemMainStatisticBinding
import com.neurotech.stressapp.ui.viewmodel.StatisticItemFragmentViewModel

class StatisticItemFragment : Fragment() {
    private var _binding: ItemMainStatisticBinding? = null
    private val binding get() = _binding!!
    val viewModel by lazy { ViewModelProvider(this)[StatisticItemFragmentViewModel::class.java] }
    val colors by lazy {
        listOf(
            ContextCompat.getColor(requireContext(), R.color.primary),
            ContextCompat.getColor(requireContext(), R.color.primary_dark),
            ContextCompat.getColor(requireContext(), R.color.primary_light),
            ContextCompat.getColor(requireContext(), R.color.secondary),
            ContextCompat.getColor(requireContext(), R.color.secondary_dark),
            ContextCompat.getColor(requireContext(), R.color.pie_chart_user1),
            ContextCompat.getColor(requireContext(), R.color.pie_chart_user2)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemMainStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sourceCount.observe(viewLifecycleOwner) {
            Log.e("Source", it.toString())
            fillTextStatistic(it)
        }
    }

    private fun fillTextStatistic(sources: List<ResultSourceCounterItem>) {
        var colorIndex = 0
        binding.sourceLayoutStatistic.removeAllViews()

        val entries = mutableListOf<PieEntry>()
        val entryColors = mutableListOf<Int>()
        var isData = false
        if (sources.isNotEmpty()) {
            sources.forEach {
                if (it.count > 0) {
                    isData = true
                }
                childFragmentManager.beginTransaction().add(
                    binding.sourceLayoutStatistic.id,
                    StatisticItemFragmentTextItem(colors[colorIndex], it.source, it.count)
                ).commit()
                entries.add(PieEntry(it.count.toFloat(), it.source))
                entryColors.add(colors[colorIndex])
                colorIndex++
            }
            if (!isData) {
                entries.add(PieEntry(1.0F, ""))
            }
            fillPieChart(entries, entryColors)
        }else{
            entryColors.add(colors[colorIndex])
            entries.add(PieEntry(1.0F, ""))
            fillPieChart(entries, entryColors)
        }

    }

    private fun fillPieChart(entries: List<PieEntry>, entryColors: List<Int>){
        val set = PieDataSet(entries,"Statistic")
        set.colors = entryColors
        val dataSet = PieData(set)
        dataSet.setValueTextColor(Color.BLACK)
        binding.pieChart.data = dataSet
        binding.pieChart.setDrawSliceText(false)
        binding.pieChart.legend.isEnabled = false
        binding.pieChart.description = null
        binding.pieChart.invalidate()


    }
}