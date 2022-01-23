package com.neurotech.stressapp.ui.fragment.mainfragmentitem

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries
import com.neurotech.stressapp.R
import com.neurotech.stressapp.ui.viewmodel.GraphFragmentViewModel
import java.util.*

class PhaseGraphFragment: Fragment() {
    private lateinit var graphView: View
    private lateinit var graph: GraphView
    private lateinit var viewModel: GraphFragmentViewModel

    private val phaseSeries = LineGraphSeries(arrayOf<DataPoint>())
    private val peakSeries = PointsGraphSeries(arrayOf<DataPoint>())
    private val maxPoint = 10_000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        graphView = inflater.inflate(R.layout.main_phasic_graph_layout,container,false)
        viewModel = ViewModelProvider(this)[GraphFragmentViewModel::class.java]
        initView()
        setObservers()
        return graphView
    }

    private fun initView(){
        graph = graphView.findViewById(R.id.phase_graph_main)
        settingGraph()
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
        graph.addSeries(phaseSeries)
        graph.addSeries(peakSeries)
        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.isXAxisBoundsManual = false
        graph.viewport.setMinY(-3.0)
        graph.viewport.setMaxY(3.0)
        graph.viewport.setMinX(0.0)
        graph.viewport.setMaxX(15000.0)
        graph.viewport.isScalable = true
        graph.viewport.isScrollable = true
        graph.viewport.setScalableY(false)
        graph.viewport.setScrollableY(false)
        graph.setBackgroundColor(Color.WHITE)
        graph.gridLabelRenderer.gridColor = Color.WHITE
        graph.gridLabelRenderer.isHorizontalLabelsVisible = false
        graph.gridLabelRenderer.isVerticalLabelsVisible = false
        graph.gridLabelRenderer.setHumanRounding(false)
        graph.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity)
        graph.gridLabelRenderer.numHorizontalLabels = 3 // only 4 because of the space
        peakSeries.color = Color.RED
        peakSeries.size = 3f
        phaseSeries.color = Color.BLACK
    }
}