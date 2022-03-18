package com.neurotech.stressapp.ui.fragment.mainfragmentitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.databinding.ItemMainTonicBinding
import com.neurotech.stressapp.ui.viewmodel.TonicItemFragmentViewModel


class TonicItemFragment : Fragment() {
    private var _binding: ItemMainTonicBinding? = null
    private val binding  get() = _binding!!

    private lateinit var viewModel: TonicItemFragmentViewModel
    private val timeInterval = arrayOf(Singleton.TEN_MINUTE,Singleton.HOUR,Singleton.DAY)
    private var indexInterval = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemMainTonicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TonicItemFragmentViewModel::class.java]
        initView()
        setObserves()
    }

    private fun initView(){
        binding.timeRangeTonic.text = timeInterval[indexInterval]
        binding.timeRangeTonic.setOnClickListener {
            indexInterval++
            if(indexInterval == 3){
                indexInterval = 0
            }
            binding.timeRangeTonic.text = timeInterval[indexInterval]
            viewModel.setInterval(timeInterval[indexInterval])
        }
    }

    private fun setObserves(){
        viewModel.tonicValue.observe(viewLifecycleOwner){
            binding.currentValue.text = it.toString()
            binding.scale.value = it
        }
        viewModel.avgTonic.observe(viewLifecycleOwner){
            binding.avgValue.text = it.toString()
        }

    }
}

