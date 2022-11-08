package com.neurotech.stressapp.ui.Main.StatisticItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neurotech.stressapp.databinding.ItemMainStatisticSourceBinding

class StatisticItemFragmentTextItem: Fragment() {

    companion object{
        const val COLOR_BUNDLE_KEY = "COLOR"
        const val SOURCE_BUNDLE_KEY = "SOURCE"
        const val COUNT_BUNDLE_KEY = "COUNT"
    }

    private var _binding: ItemMainStatisticSourceBinding? = null
    private val binding get() = _binding!!
    private val color by lazy { requireArguments().getInt(COLOR_BUNDLE_KEY) }
    private val source by lazy { requireArguments().getString(SOURCE_BUNDLE_KEY) }
    private val count by lazy { requireArguments().getInt(COUNT_BUNDLE_KEY) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemMainStatisticSourceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(arguments != null){
            binding.imageColorSources.setColorFilter(color)
            val gradientDrawable = binding.imageColorSources.background.current
            binding.imageColorSources.background = gradientDrawable.apply {
                this.setTint(color)
            }
            binding.nameSources.text = source
            binding.countSourcesStatistic.text = count.toString()
        }
    }

}