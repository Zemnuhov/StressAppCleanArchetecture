package com.neurotech.stressapp.ui.main.StatisticItem

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.ItemMainStatisticBinding
import javax.inject.Inject

class StatisticItemFragment : Fragment() {

    companion object{
        const val BUNDLE_KEY = "VISIBILITY"
    }

    @Inject
    lateinit var factory: StatisticItemFragmentViewModelFactory

    private var _binding: ItemMainStatisticBinding? = null
    private val binding get() = _binding!!
    val viewModel by lazy { ViewModelProvider(this, factory)[StatisticItemFragmentViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        _binding = ItemMainStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(arguments?.getString(BUNDLE_KEY) != null){
            binding.settingIcon.visibility = View.GONE
        }
        binding.settingIcon.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_statisticFragment)
        }

        viewModel.isRecoding.observe(viewLifecycleOwner){
            val gradientDrawable = binding.imageView3.background.current
            if(it){
                binding.imageView3.background = gradientDrawable.apply {
                    this.setTint(ContextCompat.getColor(requireContext(), R.color.red_active))
                }
            }else {
                binding.imageView3.background = gradientDrawable.apply {
                    this.setTint(ContextCompat.getColor(requireContext(), R.color.card_background))
                }
            }

        }
    }

}