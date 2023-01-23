package com.neurotech.stressapp.ui.main.StatisticItem

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.ItemMainStatisticBinding
import javax.inject.Inject

class StatisticItemFragment : Fragment() {

    companion object{
        const val BUNDLE_KEY = "VISIBILITY"
    }

    @Inject
    lateinit var factory: StatisticItemFragmentViewModelFactory

    private var _binding: ItemMainStatisticBinding? = null
    private val binding get() = _binding!!
    val viewModel by lazy { ViewModelProvider(this, factory)[StatisticItemFragmentViewModel::class.java] }
    private val colors by lazy {
        listOf(
            ContextCompat.getColor(requireContext(), R.color.primary),
            ContextCompat.getColor(requireContext(), R.color.primary_dark),
            ContextCompat.getColor(requireContext(), R.color.primary_light),
            ContextCompat.getColor(requireContext(), R.color.secondary),
            ContextCompat.getColor(requireContext(), R.color.secondary_dark),
            ContextCompat.getColor(requireContext(), R.color.third_dark),
            ContextCompat.getColor(requireContext(), R.color.third)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        _binding = ItemMainStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(arguments?.getString(BUNDLE_KEY) != null){
            binding.settingIcon.visibility = View.GONE
        }

        viewModel.sourceCount.observe(viewLifecycleOwner) {
            Log.e("Source", it.toString())
            fillTextStatistic(it)
        }
        binding.settingIcon.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_statisticFragment)
        }
    }

    private fun fillTextItem(color: Int, count: Int, source: String){
        val fragmentTextItem = StatisticItemFragmentTextItem()
        val bundle = bundleOf(
            StatisticItemFragmentTextItem.COLOR_BUNDLE_KEY to color,
            StatisticItemFragmentTextItem.COUNT_BUNDLE_KEY to count,
            StatisticItemFragmentTextItem.SOURCE_BUNDLE_KEY to source
        )
        fragmentTextItem.arguments = bundle

        childFragmentManager.beginTransaction().add(
            binding.sourceLayoutStatistic.id,
            fragmentTextItem
        ).commit()
    }

    private fun fillTextStatistic(sources: List<ResultCountSourceDomainModel>) {
        var colorIndex = 0
        binding.sourceLayoutStatistic.removeAllViews()

        val entries = mutableListOf<PieEntry>()
        val entryColors = mutableListOf<Int>()
        var isData = false
        if (sources.isNotEmpty()) {
            run breaking@{
                sources.forEach {
                    if (it.count > 0) {
                        isData = true
                    }

                    fillTextItem(colors[colorIndex],it.count,it.source)

                    entries.add(PieEntry(it.count.toFloat(), it.source))
                    entryColors.add(colors[colorIndex])
                    colorIndex++
                    if (colorIndex == colors.size) return@breaking
                }
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
        //TODO("Надо упростить")
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
        binding.pieChart.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_background))
        binding.pieChart.setHoleColor(ContextCompat.getColor(requireContext(), R.color.card_background))
        binding.pieChart.invalidate()


    }
}