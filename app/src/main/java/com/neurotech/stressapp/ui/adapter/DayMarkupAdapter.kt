package com.neurotech.stressapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neurotech.stressapp.R
import com.neurotech.stressapp.data.database.entity.MarkupEntity

class DayMarkupAdapter(private val markups: List<MarkupEntity>): RecyclerView.Adapter<DayMarkupAdapter.MarkupViewHolder>() {

    var dayMarkupCallback: DayMarkupCallback? = null

    interface DayMarkupCallback{
        fun deleteMarkup(markup: MarkupEntity)
        fun showUpdateFragment(markup: MarkupEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkupViewHolder {
        return MarkupViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_settings_day_markup,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MarkupViewHolder, position: Int) {
        holder.markupTitle.text = markups[position].markupName
        holder.markupTitle.setOnClickListener {
            dayMarkupCallback?.showUpdateFragment(markups[position])
        }
        holder.timeTextView.text = markups[position].time?: "Время не задано"
        if(markups[position].firstSource != null || markups[position].secondSource != null){
            if(markups[position].firstSource != null){
                holder.firstSource.text = markups[position].firstSource
            }else{
                holder.firstSource.visibility = View.GONE
            }

            if(markups[position].secondSource != null){
                holder.secondSource.text = markups[position].secondSource
            }else{
                holder.secondSource.visibility = View.GONE
            }
        }else{
            holder.sourceLayout.visibility = View.GONE
        }

        holder.deleteButton.setOnClickListener {
            dayMarkupCallback?.deleteMarkup(markups[position])
        }
    }

    override fun getItemCount(): Int {
        return markups.size
    }

    class MarkupViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val markupTitle: TextView = itemView.findViewById(R.id.markupTitle)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val firstSource: TextView = itemView.findViewById(R.id.firstSource)
        val secondSource: TextView = itemView.findViewById(R.id.secondSource)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteMurkupButton)
        val sourceLayout: LinearLayout = itemView.findViewById(R.id.sourceLayoutInMarkup)
    }
}
