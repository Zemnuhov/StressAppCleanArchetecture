package com.neurotech.stressapp.ui.relax

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentRelaxBinding
import com.neurotech.stressapp.ui.main.Graph.PhaseGraphFragment
import javax.inject.Inject

class RelaxFragment: Fragment(R.layout.fragment_relax) {

    @Inject
    lateinit var factory: RelaxFragmentViewModelFactory
    private var _binding: FragmentRelaxBinding? = null
    private val binding: FragmentRelaxBinding get() = _binding!!
    private val relaxViews by lazy {
        listOf(binding.relaxScaleView, binding.relaxScaleView2)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, factory)[RelaxFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        _binding = FragmentRelaxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            childFragmentManager.beginTransaction().add(linearLayout5.id, PhaseGraphFragment()).commit()
            viewModel.tonicValue.observe(viewLifecycleOwner){
                tonicValue.text = it.toString()
                relaxViews.map { view -> view.value = it }
            }
            viewModel.beginTonicValue.observe(viewLifecycleOwner){
                relaxViews.map { view -> view.beginValue = it }
            }
            viewModel.peaksInSession.observe(viewLifecycleOwner){
                peaks.text = it.toString()
            }
            viewModel.tonicDifference.observe(viewLifecycleOwner){
                tonicDifference.text = it.toString()
            }
            viewModel.timeSession.observe(viewLifecycleOwner){
                time.text = it
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

}