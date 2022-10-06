package com.neurotech.stressapp.ui.Statistic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cesarferreira.tempo.toDate
import com.cesarferreira.tempo.toString
import com.neurotech.domain.ThresholdValues
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.stressapp.R

class StatisticFragmentAdapter(private val results: List<ResultDomainModel>) :
    RecyclerView.Adapter<StatisticFragmentAdapter.StatisticCardView>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticCardView {
        context = parent.context
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_statistic_result, parent, false)
        return StatisticCardView(view)
    }

    override fun onBindViewHolder(holder: StatisticCardView, position: Int) {
        val dateTime = results[position].time
        holder.timeTextView.text = dateTime.toString(TimeFormat.timeFormat)
        holder.dateTextView.text = dateTime.toString(TimeFormat.dateFormat)
        holder.peakTextView.text = results[position].peakCount.toString()
        holder.tonicTextView.text = results[position].tonicAvg.toString()
        holder.sourceTextView.text = results[position].stressCause ?: ""
        when (results[position].peakCount) {
            in 0..ThresholdValues.normal.toInt() -> {
                holder.colorLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green_active))
            }
            in ThresholdValues.normal.toInt()..ThresholdValues.high.toInt() -> {
                holder.colorLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow_active))
            }
            else -> {
                holder.colorLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red_active))
            }
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }

    class StatisticCardView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.timeStatisticTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateStatisticTextView)
        val peakTextView: TextView = itemView.findViewById(R.id.peakCountStatisticTextView)
        val tonicTextView: TextView = itemView.findViewById(R.id.avgTonicStatisticTextView)
        val sourceTextView: TextView = itemView.findViewById(R.id.sourceStatisticTextView)
        val colorLayout: ConstraintLayout = itemView.findViewById(R.id.colorLayoutStatisticTextView)
    }
}