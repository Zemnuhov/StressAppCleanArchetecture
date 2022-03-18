package com.neurotech.stressapp.ui.fragment.mainfragmentitem

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
import com.jjoe64.graphview.series.PointsGraphSeries
import com.neurotech.stressapp.databinding.ItemMainPhaseGraphBinding
import com.neurotech.stressapp.ui.viewmodel.GraphFragmentViewModel
import java.util.*

class PhaseGraphFragment: Fragment() {
    private var _binding: ItemMainPhaseGraphBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GraphFragmentViewModel

    private val phaseSeries = LineGraphSeries(arrayOf<DataPoint>())
    private val peakSeries = PointsGraphSeries(arrayOf<DataPoint>())
    private val maxPoint = 10_000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemMainPhaseGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[GraphFragmentViewModel::class.java]
        settingGraph()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun setObservers(){
        viewModel.phaseValue.observe(viewLifecycleOwner){
            val x = it["time"] as Date
            val y = it["value"] as Double
            val point = DataPoint(x,y)
            if(y>viewModel.threshold){
                peakSeries.appendData(point,true,maxPoint)
            }
            phaseSeries.appendData(point,true,maxPoint)
        }
    }

    private fun settingGraph() {
        binding.phaseGraphMain.addSeries(phaseSeries)
        binding.phaseGraphMain.addSeries(peakSeries)
        binding.phaseGraphMain.viewport.isYAxisBoundsManual = true
        binding.phaseGraphMain.viewport.isXAxisBoundsManual = false
        binding.phaseGraphMain.viewport.setMinY(-3.0)
        binding.phaseGraphMain.viewport.setMaxY(3.0)
        binding.phaseGraphMain.viewport.setMinX(0.0)
        binding.phaseGraphMain.viewport.setMaxX(15000.0)
        binding.phaseGraphMain.viewport.isScalable = true
        binding.phaseGraphMain.viewport.isScrollable = true
        binding.phaseGraphMain.viewport.setScalableY(false)
        binding.phaseGraphMain.viewport.setScrollableY(false)
        binding.phaseGraphMain.setBackgroundColor(Color.WHITE)
        binding.phaseGraphMain.gridLabelRenderer.gridColor = Color.WHITE
        binding.phaseGraphMain.gridLabelRenderer.isHorizontalLabelsVisible = false
        binding.phaseGraphMain.gridLabelRenderer.isVerticalLabelsVisible = false
        binding.phaseGraphMain.gridLabelRenderer.setHumanRounding(false)
        binding.phaseGraphMain.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity)
        binding.phaseGraphMain.gridLabelRenderer.numHorizontalLabels = 3
        peakSeries.color = Color.RED
        peakSeries.size = 3f
        phaseSeries.color = Color.BLACK
    }
}