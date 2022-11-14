package com.neurotech.stressapp.ui.setting.Source

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neurotech.stressapp.R

class SourceAdapter(private val sourcesList: List<String>): RecyclerView.Adapter<SourceAdapter.SourceHolder>() {

    var callback: DeleteCallback? = null

    interface DeleteCallback{
        fun deleteSource(source: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_settings_source,parent,false)
        return SourceHolder(view)
    }

    override fun onBindViewHolder(holder: SourceHolder, position: Int) {
        holder.sourceTitle.text = sourcesList[position]
        holder.deleteButton.setOnClickListener{
            callback?.deleteSource(sourcesList[position])
        }
    }

    override fun getItemCount(): Int {
        return sourcesList.size
    }

    class SourceHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sourceTitle: TextView = itemView.findViewById(R.id.title_stress)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleted_source)
    }
}