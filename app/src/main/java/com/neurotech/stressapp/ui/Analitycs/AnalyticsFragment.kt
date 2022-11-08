package com.neurotech.stressapp.ui.Analitycs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cesarferreira.tempo.*
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.neurotech.domain.ThresholdValues
import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.models.ResultForTheDayDomainModel
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentAnalyticsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnalyticsFragment : Fragment(R.layout.fragment_analytics) {

    @Inject
    lateinit var factory: AnalyticsViewModelFactory
    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!
    private val appColors by lazy {
        mutableListOf<Int>().apply {
            add(ContextCompat.getColor(requireContext(), R.color.green_active))
            add(ContextCompat.getColor(requireContext(), R.color.yellow_active))
            add(ContextCompat.getColor(requireContext(), R.color.red_active))
        }
    }
    private val viewModel by lazy {
        ViewModelProvider(this, factory)[AnalyticsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setUpCalendar()
        binding.previousMonth.setOnClickListener {
            viewModel.previousMonth()
        }
        binding.nextMonth.setOnClickListener {
            viewModel.nextMonth()
        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            launch(Dispatchers.Main) {
                binding.calendarView.scrollToDate(Tempo.now)
            }
        }
    }


    private fun observeData(){
        viewModel.setInterval(
            Tempo.now.beginningOfDay,
            Tempo.now.endOfDay
        )
        viewModel.resultsInInterval.observe(viewLifecycleOwner) {
            settingIntervalGraph(getIntervalData(it))
        }
        viewModel.resultsInMonth.observe(viewLifecycleOwner) {
            settingMonthGraph(getMonthData(it))
        }
    }

    private fun setUpCalendar() {
        val startCalendar = Tempo.now - 1.year
        val endOfCalendar = Tempo.now
        binding.calendarView.apply {
            setRangeDate(startCalendar.beginningOfDay, endOfCalendar.endOfDay)
            setSelectionDate(Tempo.now)
        }
        binding.calendarView.setOnRangeSelectedListener { startDate, endDate, _, _ ->
            viewModel.setInterval(
                startDate.beginningOfDay,
                endDate.endOfDay
            )
        }
        binding.calendarView.setOnStartSelectedListener { startDate, _ ->
            viewModel.setInterval(
                startDate.beginningOfDay,
                startDate.endOfDay
            )
        }
        binding.calendarView.invalidate()
    }

    private fun getIntervalData(intervalEntity: List<ResultCountSourceDomainModel>): BarData {
        val dataSet = BarDataSet(arrayListOf(), "")
        val yValueList = mutableListOf<Float>()
        val sourceMap = mutableMapOf<Float, String>()
        val colorMap = mutableMapOf<Float, Int>()
        var id = 0.01F
        intervalEntity.forEachIndexed { index, entity ->
            val y = entity.count.toFloat()
            val x = index.toFloat()
            dataSet.addEntry(BarEntry(x, y))
            yValueList.add(y)
            sourceMap[x] = entity.source
            id += 0.1F
        }

        yValueList.sortedBy { it }.reversed().forEachIndexed { index, fl ->
            when (index) {
                0 -> colorMap[fl] = appColors[2]
                1 -> colorMap[fl] = appColors[1]
                else -> colorMap[fl] = appColors[0]
            }
        }

        dataSet.apply {
            colors = mutableListOf<Int?>().apply {
                yValueList.forEach {
                    add(colorMap[it])
                }
            }
            valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return when (value) {
                        0.0F -> ""
                        else -> "${value.toInt()}"
                    }
                }
            }
        }
        binding.rangeGraph.xAxis.labelCount = intervalEntity.size
        binding.rangeGraph.xAxis.valueFormatter = object : IndexAxisValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return sourceMap[value] ?: ""
            }
        }
        return BarData(dataSet)
    }

    private fun settingIntervalGraph(data: BarData) {
        binding.rangeGraph.apply {
            setFitBars(true)
            axisLeft.setDrawGridLines(false)
            axisLeft.setDrawAxisLine(false)
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.textSize = 7F
            xAxis.labelRotationAngle = 45f
            viewPortHandler.setMinMaxScaleX(1F, 1F)
            viewPortHandler.setMinMaxScaleY(1F, 1F)
            legend.isEnabled = false
            description = Description().apply { text = "" }
            setData(data)
            animateY(500)
            invalidate()
        }
    }

    private fun getMonthData(resultEntityList: List<ResultForTheDayDomainModel>): BarData {
        val barEntryList = mutableListOf<BarEntry>()
        val sourceMap = mutableMapOf<Float, String>()
        val yValueList = mutableListOf<Float>()
        var id = 0.01f
        resultEntityList.sortedBy { it.date }.forEach {
            val x = it.date.toString("dd").toFloat()
            val y = it.peaks + id
            barEntryList.add(BarEntry(x, y))
            sourceMap[y] = it.stressCause
            yValueList.add(y)
            id += 0.01f
        }
        val dataSet = BarDataSet(barEntryList, "")

        dataSet.apply {
            valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    if (value.toInt() == 0) return ""
                    return sourceMap[value] ?: ""
                }
            }
            colors = mutableListOf<Int>().apply {
                yValueList.forEach { yValue ->
                    if (yValue < ThresholdValues.normalDay) {
                        add(appColors[0])
                    } else if (yValue > ThresholdValues.normalDay && yValue < ThresholdValues.highDay) {
                        add(appColors[1])
                    } else {
                        add(appColors[2])
                    }
                }
            }
        }
        binding.staticGraphMP.xAxis.labelCount = resultEntityList.size
        return BarData(dataSet)
    }

    private fun settingMonthGraph(data: BarData) {
        binding.staticGraphMP.apply {
            setFitBars(true)
            setData(data)
            axisLeft.setDrawGridLines(false)
            axisLeft.setDrawAxisLine(false)
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.labelRotationAngle = 45f
            xAxis.textSize = 7F
            xAxis.valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "${value.toInt()}"
                }
            }
            viewPortHandler.setMinMaxScaleX(1F, 1F)
            viewPortHandler.setMinMaxScaleY(1F, 1F)
            legend.isEnabled = false
            description = Description().apply { text = "" }
            animateY(500)
            setOnChartValueSelectedListener(object : OnChartValueSelectedListener{
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    if (e != null){
                        val day = viewModel.monthDate.with(day = e.x.toInt())
                        if(day<Tempo.now.endOfDay){
                            binding.calendarView.scrollToDate(day)
                            binding.calendarView.setSelectionDate(day+1.day)
                            binding.calendarView.setSelectionDate(day)
                        }
                    }
                }

                override fun onNothingSelected() {

                }

            })
        }
    }

    override fun onStop() {
        super.onStop()
        binding.calendarView.removeAllViews()
    }

}