package com.neurotech.stressapp.ui.Main.PhaseItem

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cesarferreira.tempo.toDate
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.neurotech.domain.ThresholdValues
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.databinding.ItemMainPhaseBinding
import javax.inject.Inject


class PhaseItemFragment : Fragment() {

    @Inject
    lateinit var factory: PhaseItemFragmentViewModelFactory

    private var _binding: ItemMainPhaseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhaseItemFragmentViewModel by lazy {
        ViewModelProvider(this, factory)[PhaseItemFragmentViewModel::class.java]
    }

    private val timeInterval = arrayOf(Singleton.TEN_MINUTE, Singleton.HOUR, Singleton.DAY)
    private var indexInterval = 0

    private var barSeries = BarGraphSeries(arrayOf<DataPoint>())
    private val timeFormat = "yyyy-MM-dd HH:mm:ss.SSS"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        _binding = ItemMainPhaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        graphSetting()
        setObservers()
        binding.timeRangePhase.text = timeInterval[indexInterval]
        binding.timeRangePhase.setOnClickListener {
            indexInterval++
            if (indexInterval == 3) {
                indexInterval = 0
            }
            binding.timeRangePhase.text = timeInterval[indexInterval]
            viewModel.setInterval(timeInterval[indexInterval])
        }
        viewModel.setInterval(timeInterval[indexInterval])
    }

    private fun setObservers() {
        viewModel.peakCount.observe(viewLifecycleOwner) {
            binding.peaksCounter.text = it.toString()
        }

        viewModel.resultsInHour.observe(viewLifecycleOwner) { it ->
            barSeries = BarGraphSeries(arrayOf<DataPoint>())
            it.forEach{
                barSeries.appendData(
                    DataPoint(
                        it.time.toDate(timeFormat),
                        it.peakCount.toDouble()),
                    true,
                    6)
            }
            graphSetting()
            binding.peaksCounterGraph.invalidate()
        }
    }


    private fun graphSetting() {
        binding.peaksCounterGraph.removeAllSeries()
        binding.peaksCounterGraph.viewport.isXAxisBoundsManual = true
        binding.peaksCounterGraph.viewport.isYAxisBoundsManual = true
        binding.peaksCounterGraph.viewport.setMinX(barSeries.lowestValueX-500000)
        binding.peaksCounterGraph.viewport.setMaxX(barSeries.highestValueX + 500000)
        binding.peaksCounterGraph.viewport.setMinY(0.0)
        binding.peaksCounterGraph.viewport.setMaxY(barSeries.highestValueY + 2)
        binding.peaksCounterGraph.viewport.isScalable = false
        binding.peaksCounterGraph.viewport.isScrollable = false
        binding.peaksCounterGraph.viewport.setScalableY(false)
        binding.peaksCounterGraph.viewport.setScrollableY(false)
        binding.peaksCounterGraph.setBackgroundColor(Color.WHITE)
        binding.peaksCounterGraph.gridLabelRenderer.gridColor = Color.BLACK
        binding.peaksCounterGraph.gridLabelRenderer.isVerticalLabelsVisible = false
        binding.peaksCounterGraph.gridLabelRenderer.isHorizontalLabelsVisible = false
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
        binding.peaksCounterGraph.addSeries(barSeries)
    }


}