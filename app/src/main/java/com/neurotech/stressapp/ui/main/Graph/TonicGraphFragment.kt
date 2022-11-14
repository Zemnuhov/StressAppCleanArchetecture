package com.neurotech.stressapp.ui.main.Graph

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.neurotech.stressapp.App
import com.neurotech.stressapp.databinding.ItemMainPhaseGraphBinding
import javax.inject.Inject

class TonicGraphFragment: Fragment() {
    @Inject
    lateinit var factory: GraphFragmentViewModelFactory

    private var _binding: ItemMainPhaseGraphBinding? = null
    private val binding get() = _binding!!

    val viewModel by lazy { ViewModelProvider(this, factory)[GraphFragmentViewModel::class.java] }

    private var tonicSeries = LineGraphSeries(arrayOf<DataPoint>())
    private val maxPoint = 5_000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        _binding = ItemMainPhaseGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        settingGraph()
        setObservers()
        binding.phaseSwapButton.setOnClickListener {
            childFragmentManager.beginTransaction().replace(view.id, PhaseGraphFragment()).commit()
        }
    }


    private fun setObservers() {
        viewModel.tonicValue.observe(viewLifecycleOwner) {
            val x = it.time
            val y = it.value.toDouble()
            val point = DataPoint(x, y)
            if(tonicSeries.highestValueX < x.time) {
                tonicSeries.appendData(point, true, maxPoint)
                binding.phaseGraphMain.viewport.setMinY(y - 1000)
                binding.phaseGraphMain.viewport.setMaxY(y + 1000)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val points = viewModel.tonicValuesInMemory.map { DataPoint(it.time, it.value.toDouble()) }.toTypedArray()
        tonicSeries = LineGraphSeries(points)
        settingGraph()
    }

    private fun settingGraph() {
        binding.phaseGraphMain.removeAllSeries()
        binding.phaseGraphMain.addSeries(tonicSeries)
        binding.phaseGraphMain.viewport.isYAxisBoundsManual = true
        binding.phaseGraphMain.viewport.isXAxisBoundsManual = false
        binding.phaseGraphMain.viewport.setMinX(0.0)
        binding.phaseGraphMain.viewport.setMaxX(30000.0)
        binding.phaseGraphMain.viewport.isScalable = true
        binding.phaseGraphMain.viewport.isScrollable = true
        binding.phaseGraphMain.viewport.setScalableY(false)
        binding.phaseGraphMain.viewport.setScrollableY(false)
        binding.phaseGraphMain.setBackgroundColor(Color.WHITE)
        binding.phaseGraphMain.gridLabelRenderer.gridColor = Color.WHITE
        binding.phaseGraphMain.gridLabelRenderer.isHorizontalLabelsVisible = false
        binding.phaseGraphMain.gridLabelRenderer.isVerticalLabelsVisible = false
        binding.phaseGraphMain.gridLabelRenderer.setHumanRounding(false)
        binding.phaseGraphMain.gridLabelRenderer.labelFormatter =
            DateAsXAxisLabelFormatter(activity)
        binding.phaseGraphMain.gridLabelRenderer.numHorizontalLabels = 3
        tonicSeries.color = Color.BLACK
    }
}