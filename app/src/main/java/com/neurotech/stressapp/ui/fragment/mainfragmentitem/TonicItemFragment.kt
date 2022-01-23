package com.neurotech.stressapp.ui.fragment.mainfragmentitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neurotech.stressapp.R
import com.neurotech.stressapp.ui.viewmodel.TonicItemFragmentViewModel


class TonicItemFragment : Fragment() {

    private lateinit var viewModel: TonicItemFragmentViewModel
    private lateinit var tonicValueEditText: TextView
    private lateinit var tonicAvgValueEditText: TextView
    lateinit var scale: ScaleView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tonicItemView = inflater.inflate(R.layout.main_avg_current_value,container,false)
        viewModel = ViewModelProvider(this)[TonicItemFragmentViewModel::class.java]
        initView(tonicItemView)
        setObserves()
        return tonicItemView
    }

    private fun initView(view: View){
        tonicValueEditText = view.findViewById(R.id.current_value)
        tonicAvgValueEditText = view.findViewById(R.id.avg_value)
        scale = ScaleView()
        childFragmentManager.beginTransaction()
            .replace(R.id.scale_fragment, scale)
            .commit()
    }

    private fun setObserves(){
        viewModel.tonicValue.observe(viewLifecycleOwner){
            tonicValueEditText.text = it.toString()
            scale.setScale(it)
        }
        viewModel.avgTonic.observe(viewLifecycleOwner){
            tonicAvgValueEditText.text = it.toString()
        }

    }
}

