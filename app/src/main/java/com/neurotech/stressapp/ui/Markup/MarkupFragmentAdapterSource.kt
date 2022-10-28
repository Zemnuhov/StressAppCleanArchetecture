package com.neurotech.stressapp.ui.Markup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neurotech.stressapp.databinding.ItemMarkupSourceBinding

class MarkupFragmentAdapterSource(private val sourceList:List<String>): RecyclerView.Adapter<SourceHolder>() {

    interface Callback{
        fun clickBySource(source: String)
    }

    var callback: Callback? = null
    private lateinit var itemBinding: ItemMarkupSourceBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceHolder {
        itemBinding = ItemMarkupSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SourceHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SourceHolder, position: Int) {
        val source = sourceList[position]
        holder.bind(source, callback!!)
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }
}

class SourceHolder(private val itemBinding: ItemMarkupSourceBinding) : RecyclerView.ViewHolder(itemBinding.root){
    fun bind(source: String, callback: MarkupFragmentAdapterSource.Callback){
        itemBinding.sourceInMarkup.text = source
        itemBinding.itemMarkupSource.setOnClickListener {
            val xScaleAnimationPlus = ObjectAnimator.ofFloat(itemBinding.root, "scaleX", 1f)
            val yScaleAnimationPlus = ObjectAnimator.ofFloat(itemBinding.root, "scaleY", 1f)
            val xScaleAnimationMin = ObjectAnimator.ofFloat(itemBinding.root, "scaleX", 0.8f)
            val yScaleAnimationMin = ObjectAnimator.ofFloat(itemBinding.root, "scaleY", 0.8f)
            AnimatorSet().apply {
                play(xScaleAnimationMin).with(yScaleAnimationMin)
                play(xScaleAnimationPlus).with(yScaleAnimationPlus).after(yScaleAnimationMin)
                duration = 200
                start()
            }
            callback.clickBySource(source)
        }
    }

}

