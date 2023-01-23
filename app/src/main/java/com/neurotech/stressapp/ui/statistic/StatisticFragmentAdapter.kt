package com.neurotech.stressapp.ui.statistic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cesarferreira.tempo.toString
import com.neurotech.domain.ThresholdValues
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.models.UserDomain
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.ItemStatisticResultBinding
import java.util.*

class StatisticFragmentAdapter(private val results: List<ResultDomainModel>, private val normalValue: Int) :
    RecyclerView.Adapter<StatisticFragmentAdapter.StatisticCardView>() {

    val keepMap = mutableMapOf<Date, String?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticCardView {
        val itemBinding = ItemStatisticResultBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return StatisticCardView(itemBinding)
    }

    override fun onBindViewHolder(holder: StatisticCardView, position: Int) {
        val result = results[position]
        holder.textBind(result)
        holder.colorBind(result)
        holder.keepSaveButton.setOnClickListener {
            if(holder.keepEditText.text.isNotEmpty()){
                keepMap[result.time] = holder.keepEditText.text.toString()
            }else{
                keepMap[result.time] = null
            }
            holder.saveButtonClick()
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class StatisticCardView(private val itemBinding: ItemStatisticResultBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val keepEditText = itemBinding.keepEditText
        val keepSaveButton = itemBinding.keepSaveButton
        fun textBind(result: ResultDomainModel){
            itemBinding.timeStatisticTextView.text = result.time.toString(TimeFormat.timeFormat)
            itemBinding.dateStatisticTextView.text = result.time.toString(TimeFormat.dateFormat)
            itemBinding.peakCountStatisticTextView.text = result.peakCount.toString()
            itemBinding.avgTonicStatisticTextView.text = result.tonicAvg.toString()
            itemBinding.sourceStatisticTextView.text = result.stressCause ?: ""
            if(result.keep != null){
                showTextLayout(result.keep!!)
            }
            itemBinding.keepButton.setOnClickListener {
                if(itemBinding.keepEditText.visibility == View.GONE){
                    showEditLayout()
                }else{
                    if (result.keep != null){
                        showTextLayout(result.keep!!)
                    }else{
                        goneAll()
                    }
                }
            }
        }

        private fun goneAll(){
            itemBinding.keepLayout.visibility = View.GONE
            itemBinding.keepEditText.visibility = View.GONE
            itemBinding.keepSaveButton.visibility = View.GONE
            itemBinding.keepTextView.visibility = View.GONE
            itemBinding.keepString.visibility = View.GONE
        }

        private fun showEditLayout(){
            itemBinding.keepLayout.visibility = View.VISIBLE
            itemBinding.keepTextView.visibility = View.GONE
            itemBinding.keepString.visibility = View.GONE
            itemBinding.keepEditText.visibility = View.VISIBLE
            itemBinding.keepSaveButton.visibility = View.VISIBLE
            itemBinding.keepEditText.hint = "Запишите заметку"
        }

        private fun showTextLayout(keep: String){
            itemBinding.keepTextView.text = keep
            itemBinding.keepLayout.visibility = View.VISIBLE
            itemBinding.keepEditText.visibility = View.GONE
            itemBinding.keepSaveButton.visibility = View.GONE
            itemBinding.keepTextView.visibility = View.VISIBLE
            itemBinding.keepString.visibility = View.VISIBLE
        }

        fun saveButtonClick(){
            if (itemBinding.keepEditText.text.isNotEmpty()){
                showTextLayout(itemBinding.keepEditText.text.toString())
            }else{
                goneAll()
            }

        }

        fun colorBind(result: ResultDomainModel){
            val gradientDrawable = itemBinding.colorLayoutStatisticTextView.background.current
            when (result.peakCount) {
                in 0..normalValue -> {
                    itemBinding.colorLayoutStatisticTextView.background = gradientDrawable.apply {
                        this.setTint(ContextCompat.getColor(itemBinding.root.context, R.color.green_active))
                    }
                }
                in normalValue..normalValue*2 -> {
                    itemBinding.colorLayoutStatisticTextView.background = gradientDrawable.apply {
                        this.setTint(ContextCompat.getColor(itemBinding.root.context, R.color.yellow_active))
                    }
                }
                else -> {
                    itemBinding.colorLayoutStatisticTextView.background = gradientDrawable.apply {
                        this.setTint(ContextCompat.getColor(itemBinding.root.context, R.color.red_active))
                    }
                }
            }
        }
    }
}