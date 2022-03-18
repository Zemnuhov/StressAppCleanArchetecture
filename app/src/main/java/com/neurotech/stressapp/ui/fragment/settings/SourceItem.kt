package com.neurotech.stressapp.ui.fragment.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neurotech.stressapp.databinding.ItemSettingsSourceBinding
import com.neurotech.stressapp.ui.viewmodel.SourceViewModel

class SourceItem: Fragment() {
    private var _binding: ItemSettingsSourceBinding? = null
    private val binding get() = _binding!!
    val viewModel by lazy { ViewModelProvider(this)[SourceViewModel::class.java] }

    companion object{
        const val SOURCE_KEY = "SOURCE"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = ItemSettingsSourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("Sources", arguments?.getString(SOURCE_KEY).toString())
        binding.titleStress.text = arguments?.getString(SOURCE_KEY)
        binding.deletedSource.setOnClickListener{
            viewModel.deleteSource(binding.titleStress.text.toString())
        }
    }
}