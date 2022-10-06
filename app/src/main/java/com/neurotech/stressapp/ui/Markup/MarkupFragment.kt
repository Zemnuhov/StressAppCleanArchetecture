package com.neurotech.stressapp.ui.Markup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class MarkupFragment : Fragment(R.layout.fragment_markup) {

    @Inject
    lateinit var factory: MarkupFragmentViewModelFactory
    val viewModel by lazy { ViewModelProvider(this,factory)[MarkupFragmentViewModel::class.java] }
    private var _binding: FragmentMarkupBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: MarkupFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        adapter = MarkupFragmentAdapter(listOf(),viewModel.stimulus.value?: listOf())
        _binding = FragmentMarkupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createGraph()
        fillRecyclerView()
        binding.saveButton.setOnClickListener {
            for(markup in adapter.markups){
                if(markup.stressCause != null){
                    viewModel.setStressCause(markup.time.toString(TimeFormat.dateTimeFormatDataBase), markup.stressCause!!)
                }
            }
        }
    }

    private fun fillRecyclerView(){
        binding.markupRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.markupRecyclerView.adapter = adapter
        viewModel.tenMinuteResultBeyondLimit.observe(viewLifecycleOwner){
            if(it.filter {result -> result.stressCause == null }.isEmpty()){
                binding.markupRecyclerView.visibility = View.GONE
                binding.allMarkupsComplete.visibility = View.VISIBLE
            }else{
                binding.allMarkupsComplete.visibility = View.GONE
                binding.markupRecyclerView.visibility = View.VISIBLE
                adapter = MarkupFragmentAdapter(
                    it.filter {result -> result.stressCause == null },
                    viewModel.stimulus.value?: listOf()
                )
                binding.markupRecyclerView.adapter = adapter
            }
        }
    }

    private fun createGraph(){
        childFragmentManager
            .beginTransaction()
            .replace(binding.graphInMarkup.id, PhaseGraphFragment())
            .commit()
    }
}