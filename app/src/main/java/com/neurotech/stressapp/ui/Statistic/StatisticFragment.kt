package com.neurotech.stressapp.ui.Statistic

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesarferreira.tempo.toDate
import com.cesarferreira.tempo.toString
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.neurotech.domain.ThresholdValues
import com.neurotech.domain.TimeFormat
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

    private fun mapValue(value: Double, min: Int): Double {
        return value  / 10000 * (min+50 - min) + min
    }

    private fun setObservers() {
        viewModel.results.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = StatisticFragmentAdapter(it)
        }
        viewModel.results.observe(viewLifecycleOwner) {
            barSeries = BarGraphSeries(arrayOf<DataPoint>())
            tonicSeries = LineGraphSeries(arrayOf<DataPoint>())
            it.sortedBy { result -> result.time.toDate(TimeFormat.dateTimeFormatDataBase) }
                .forEach { result ->
                    val time = result.time.toDate(TimeFormat.dateTimeFormatDataBase)
                    val peaks = result.peakCount.toDouble()
                    val bar = DataPoint(time, peaks)
                    barSeries.appendData(bar, true, 10000)
                    val tonic = result.tonicAvg.toDouble()
                    val point = DataPoint(time, mapValue(tonic,it.maxOf { it.peakCount }))
                    tonicSeries.appendData(point,true, 10000)
                }
            graphSettings()
        }
    }

    private fun graphSettings() {
        binding.statisticGraph.addSeries(tonicSeries)
        binding.statisticGraph.addSeries(barSeries)

        binding.statisticGraph.viewport.isXAxisBoundsManual = true
        binding.statisticGraph.viewport.isYAxisBoundsManual = true
        binding.statisticGraph.viewport.setMinX(barSeries.lowestValueX - 500000)
        binding.statisticGraph.viewport.setMaxX(barSeries.highestValueX + 500000)
        binding.statisticGraph.viewport.setMinY(0.0)
        binding.statisticGraph.viewport.setMaxY(tonicSeries.highestValueY + 2)
        binding.statisticGraph.viewport.isScalable = true
        binding.statisticGraph.viewport.isScrollable = true
        binding.statisticGraph.viewport.setScalableY(false)
        binding.statisticGraph.viewport.setScrollableY(false)
        binding.statisticGraph.setBackgroundColor(Color.WHITE)
        binding.statisticGraph.gridLabelRenderer.gridColor = Color.GRAY
        binding.statisticGraph.gridLabelRenderer.isVerticalLabelsVisible = false

        binding.statisticGraph.gridLabelRenderer.labelFormatter = object :DefaultLabelFormatter(){
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                val date = Date(value.toLong())
                return date.toString(TimeFormat.timeFormat) + "\n" + date.toString(TimeFormat.dateFormatNoYear)
            }
        }
        binding.statisticGraph.gridLabelRenderer.numHorizontalLabels = 3

        barSeries.spacing = 1
        barSeries.dataWidth = 500000.0
        barSeries.setValueDependentColor { data: DataPoint ->
            if (data.y < ThresholdValues.normal) {
                return@setValueDependentColor ContextCompat.getColor(
                    requireContext(),
                    R.color.green_active
                )
            }
            if (data.y in ThresholdValues.normal .. ThresholdValues.high) {
                return@setValueDependentColor ContextCompat
                    .getColor(requireContext(), R.color.yellow_active)
            }
            ContextCompat.getColor(requireContext(), R.color.red_active)
        }
        tonicSeries.color = Color.BLACK
        binding.statisticGraph.invalidate()
    }
}