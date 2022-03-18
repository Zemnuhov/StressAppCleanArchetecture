package com.neurotech.stressapp.ui.fragment.mainfragmentitem

import android.graphics.ColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neurotech.stressapp.databinding.ItemMainStatisticSourceBinding

class StatisticItemFragmentTextItem(val color: Int,
                                    val source:String,
                                    val count:Int): Fragment() {
    private var _binding: ItemMainStatisticSourceBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemMainStatisticSourceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imageColorSources.setColorFilter(color)
        binding.nameSources.text = source
        binding.countSourcesStatistic.text = count.toString()
    }

}