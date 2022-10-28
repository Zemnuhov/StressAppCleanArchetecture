package com.neurotech.stressapp.ui.Markup

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cesarferreira.tempo.toString
import com.neurotech.domain.ThresholdValues
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.ItemMarkupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class MarkupFragmentAdapterMarkups(private val markups: List<ResultDomainModel>) :
    RecyclerView.Adapter<MarkupFragmentAdapterMarkups.MarkupViewHolder>() {

    private var dateList = mutableListOf<Date>()
    private val _resultMap = mutableMapOf<String, MutableList<Date>>()
    val resultMap: Map<String, List<Date>> get() = _resultMap
    lateinit var itemBinding: ItemMarkupBinding
    private val flowCheckBox = MutableStateFlow<Boolean?>(null)
    private val checkBoxList = mutableListOf<CheckBox>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkupViewHolder {
        itemBinding = ItemMarkupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        CoroutineScope(Dispatchers.Main).launch {
            flowCheckBox.collect {
                checkBoxList.forEach { box ->
                    if (it != null) {
                        box.isChecked = it
                    }
                }
            }
        }
        return MarkupViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MarkupViewHolder, position: Int) {
        val markup = markups[position]
        holder.bind(markup)
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _resultMap.forEach { (_, dateList) ->
                    if(markup.time in dateList){
                        dateList.remove(markup.time)
                    }
                }
                dateList.add(markup.time)
            } else {
                dateList.remove(markup.time)
            }
        }

        resultMap.forEach { (source, dateList) ->
            if (markup.time in dateList) {
                holder.sourceTextView.text = source
            }
        }

        checkBoxList.add(holder.checkBox)
    }

    fun setSource(source: String) {
        if (source !in _resultMap.keys) {
            _resultMap[source] = mutableListOf<Date>().apply {
                dateList.forEach {
                    add(it)
                }
            }
        } else {
            dateList.forEach {
                _resultMap[source]?.add(it)
            }
        }
        flowCheckBox.value = false
        Log.e("MARKUP", _resultMap.toString())
        dateList = mutableListOf()
    }


    override fun getItemCount(): Int {
        return markups.size
    }

    class MarkupViewHolder(private val itemBinding: ItemMarkupBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val checkBox = itemBinding.checkBox
        val sourceTextView = itemBinding.sourceTextView
        fun bind(markup: ResultDomainModel) {
            itemBinding.dateMarkupItemTextView.text = markup.time.toString(TimeFormat.dateFormat)
            itemBinding.timeMarkupItemTextView.text = markup.time.toString(TimeFormat.timeFormat)
            itemBinding.peaksMarkupItemTextView.text = markup.peakCount.toString()
            itemBinding.tonicMarkupItemTextView.text = markup.tonicAvg.toString()
            itemBinding.colorIndicator.background =
                itemBinding.colorIndicator.background.current.apply {
                    if (markup.peakCount >= ThresholdValues.normal && markup.peakCount < ThresholdValues.high) {
                        setTint(
                            ContextCompat.getColor(
                                itemBinding.root.context,
                                R.color.yellow_active
                            )
                        )
                    } else {
                        setTint(
                            ContextCompat.getColor(
                                itemBinding.root.context,
                                R.color.red_active
                            )
                        )
                    }
                }
        }
    }
}