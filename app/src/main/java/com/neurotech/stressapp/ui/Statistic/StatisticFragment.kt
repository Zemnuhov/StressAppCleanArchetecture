package com.neurotech.stressapp.ui.Statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.toDate
import com.cesarferreira.tempo.toString
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.neurotech.domain.ThresholdValues
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentStatisticBinding
import com.neurotech.stressapp.ui.Main.StatisticItem.StatisticItemFragment
import java.util.*
import javax.inject.Inject

class StatisticFragment : Fragment(R.layout.fragment_statistic) {

    @Inject
    lateinit var factory: StatisticFragmentViewModelFactory

    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            factory
        )[StatisticFragmentViewModel::class.java]
    }
    private var barSeries = BarGraphSeries(arrayOf<DataPoint>())
    private var tonicSeries = LineGraphSeries(arrayOf<DataPoint>())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun buttonListeners() {
        binding.dayButton.setOnClickListener { viewModel.setDayResults() }
        binding.weekButton.setOnClickListener { viewModel.setWeekResults() }
        binding.monthButton.setOnClickListener { viewModel.setMonthResults() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val statisticItem = StatisticItemFragment()
        statisticItem.arguments = bundleOf(StatisticItemFragment.BUNDLE_KEY to "GONE")
        childFragmentManager.beginTransaction()
            .replace(binding.mainStatisticLayout.id, statisticItem)
            .commit()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = StatisticFragmentAdapter(listOf())
        setObservers()
        buttonListeners()
    }

    private fun mapValue(value: Float, min: Int): Float {
        return value / 10000 * (min + 100 - min) + min
    }

    private fun setObservers() {
        viewModel.results.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = StatisticFragmentAdapter(it)
        }
        viewModel.results.observe(viewLifecycleOwner) {
            drawGraph(getCombinedData(it))
        }
    }

    private fun getCombinedData(resultList: List<ResultDomainModel>): CombinedData {
        val barDataSet = BarDataSet(arrayListOf(), "")
        val lineDataSet = LineDataSet(arrayListOf(), "")

        val sortedResultList =
            resultList.sortedBy { result -> result.time.toDate(TimeFormat.dateTimeFormatDataBase) }
        sortedResultList.forEach { result ->
            val time = result.time.toDate(TimeFormat.dateTimeFormatDataBase).time.toFloat()
            val peaks = result.peakCount.toFloat()
            val tonic = mapValue( result.tonicAvg.toFloat(),sortedResultList.maxOf { it.peakCount })
            barDataSet.addEntry(BarEntry(time, peaks))
            lineDataSet.addEntry(Entry(time, tonic))
        }

//        barDataSet.apply {
//            colors = mutableListOf<Int>().apply {
//                sortedResultList.map { it.peakCount }.forEach{
//                    if(it<ThresholdValues.normal){
//                        add(ContextCompat.getColor(requireContext(),R.color.green_active))
//                    }else if (it>= ThresholdValues.normal && it<ThresholdValues.high){
//                        add(ContextCompat.getColor(requireContext(),R.color.yellow_active))
//                    }else{
//                        add(ContextCompat.getColor(requireContext(),R.color.red_active))
//                    }
//                }
//            }
//        }
        val data = CombinedData().apply {
            setData(BarData(barDataSet))
            setData(LineData(lineDataSet))
        }
        return data
    }

    private fun drawGraph(data: CombinedData){
        binding.statisticGraph.apply {
            axisLeft.setDrawGridLines(false)
            axisLeft.setDrawAxisLine(false)
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.textSize = 7F
            xAxis.labelRotationAngle = 45f
            //viewPortHandler.setMinMaxScaleX(1F, 1F)
            //viewPortHandler.setMinMaxScaleY(1F, 1F)
            legend.isEnabled = false
            description = Description().apply { text = "" }
            xAxis.valueFormatter = object : IndexAxisValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return Date(value.toLong()).toString("HH-mm")
                }
            }
            setData(data)
            animateY(500)
            invalidate()
        }
    }

}