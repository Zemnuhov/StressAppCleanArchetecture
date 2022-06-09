package com.neurotech.stressapp.ui.Markup

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cesarferreira.tempo.toDate
import com.cesarferreira.tempo.toString
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.stressapp.R

class MarkupFragmentAdapter( val markups: List<ResultDomainModel>,
                             private val stimulus: List<String>):
    RecyclerView.Adapter<MarkupFragmentAdapter.MarkupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkupViewHolder {
        return MarkupViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_markup,parent, false)
        )
    }

    override fun onBindViewHolder(holder: MarkupViewHolder, position: Int) {
        holder.date.text = markups[position]
            .time
            .toDate(TimeFormat.dateTimeFormatDataBase)
            .toString(TimeFormat.dateFormat)
        holder.time.text = markups[position]
            .time
            .toDate(TimeFormat.dateTimeFormatDataBase)
            .toString(TimeFormat.timeFormat)
        holder.peaks.text = markups[position].peakCount.toString()
        holder.tonic.text = markups[position].tonicAvg.toString()
        holder.picker.minValue = 0
        holder.picker.maxValue = stimulus.size-1
        holder.picker.wrapSelectorWheel = false
        holder.picker.displayedValues = stimulus.toTypedArray()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            holder.picker.selectionDividerHeight = 0
        }
        holder.picker.setOnValueChangedListener{ _, _, newVal ->
            markups[position].stressCause = stimulus[newVal]
        }

    }

    override fun getItemCount(): Int {
        return markups.size
    }

    class MarkupViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val time: TextView = itemView.findViewById(R.id.timeMarkupItemTextView)
        val date: TextView = itemView.findViewById(R.id.dateMarkupItemTextView)
        val peaks: TextView = itemView.findViewById(R.id.peaksMarkupItemTextView)
        val tonic: TextView = itemView.findViewById(R.id.tonicMarkupItemTextView)
        val picker: NumberPicker = itemView.findViewById(R.id.sourcePickerMarkupItem)
    }
}