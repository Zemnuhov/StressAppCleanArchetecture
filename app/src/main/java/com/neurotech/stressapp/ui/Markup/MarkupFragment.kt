package com.neurotech.stressapp.ui.Markup

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesarferreira.tempo.toString
import com.neurotech.domain.TimeFormat
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentMarkupBinding

import com.neurotech.stressapp.ui.Main.Graph.PhaseGraphFragment
import javax.inject.Inject

class MarkupFragment : Fragment(R.layout.fragment_markup), MarkupFragmentAdapterSource.Callback {

    @Inject
    lateinit var factory: MarkupFragmentViewModelFactory
    val viewModel by lazy { ViewModelProvider(this,factory)[MarkupFragmentViewModel::class.java] }
    private var _binding: FragmentMarkupBinding? = null
    private val binding get() = _binding!!
    private var adapterMarkup: MarkupFragmentAdapterMarkups = MarkupFragmentAdapterMarkups(listOf())
    private var adapterSource: MarkupFragmentAdapterSource = MarkupFragmentAdapterSource(listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        _binding = FragmentMarkupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createGraph()
        fillMarkupRecyclerView()
        fillSourceRecyclerView()
        binding.saveButton.setOnClickListener {
            adapterMarkup.resultMap.forEach{ (source, dateList) ->
                viewModel.setStressCause(source, dateList)
            }
        }

    }

    private fun fillMarkupRecyclerView(){
        binding.markupRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.markupRecyclerView.adapter = adapterMarkup
        viewModel.tenMinuteResultBeyondLimit.observe(viewLifecycleOwner){
            if(it.filter {result -> result.stressCause == null }.isEmpty()){
                binding.markupRecyclerView.visibility = View.GONE
                binding.allMarkupsComplete.visibility = View.VISIBLE
            }else{
                binding.allMarkupsComplete.visibility = View.GONE
                binding.markupRecyclerView.visibility = View.VISIBLE
                adapterMarkup = MarkupFragmentAdapterMarkups(
                    it.filter {result -> result.stressCause == null })
                binding.markupRecyclerView.setItemViewCacheSize(adapterMarkup.itemCount)
                binding.markupRecyclerView.adapter = adapterMarkup
            }
        }
    }

    private fun fillSourceRecyclerView(){
        binding.sourceRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        binding.sourceRecyclerView.adapter = adapterSource
        viewModel.stimulus.observe(viewLifecycleOwner){
            binding.sourceRecyclerView.setItemViewCacheSize(it.size)
            setAdapterBySourceRecycler(it)
        }
    }

    private fun setAdapterBySourceRecycler(sources: List<String>){
        adapterSource = MarkupFragmentAdapterSource(sources)
        binding.sourceRecyclerView.adapter = adapterSource
        adapterSource.callback = this
    }

    private fun createGraph(){
        childFragmentManager
            .beginTransaction()
            .replace(binding.graphInMarkup.id, PhaseGraphFragment())
            .commit()
    }

    override fun clickBySource(source: String) {
        adapterMarkup.setSource(source)
        adapterMarkup.notifyItemRangeChanged(0,adapterMarkup.itemCount)
        binding.sourceRecyclerView.setItemViewCacheSize(adapterMarkup.itemCount)
    }
}