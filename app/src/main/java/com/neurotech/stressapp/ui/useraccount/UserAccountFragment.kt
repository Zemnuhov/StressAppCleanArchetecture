package com.neurotech.stressapp.ui.useraccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserAccountFragment: Fragment(R.layout.fragment_user) {
    @Inject
    lateinit var factory: UserAccountViewModelFactory
    val viewModel by lazy { ViewModelProvider(this, factory)[UserAccountViewModel::class.java] }

    private var _binding: FragmentUserBinding? = null
    private val binding: FragmentUserBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as App).component.inject(this)
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        binding.switchButton2.setText("День", "Месяц", "Год")
        CoroutineScope(Dispatchers.IO).launch {
            binding.switchButton2.state.collect{
                when(it){
                    1 -> viewModel.setOneDayInterval()
                    2 -> viewModel.setOneMonthInterval()
                    3 -> viewModel.setOneYearInterval()
                }
            }
        }
        viewModel.normalPeakDay.observe(viewLifecycleOwner){
            binding.normalPeakCountInDayTextView.text = it.toString()
        }

        viewModel.normalTonic.observe(viewLifecycleOwner){
            binding.normalTonic.text = it.toString()
        }

        viewModel.normalPeakTenMinute.observe(viewLifecycleOwner){
            binding.normalPeakCountInTenMinuteTextView.text = it.toString()
        }

        viewModel.userParameter.observe(viewLifecycleOwner){
            with(binding){
                avgTonic.text = it.tonicAverage.toString()
                avgPeakCountInDayTextView.text = it.peaksInDay.toString()
                avgPeakCountInTenMinuteTextView.text = it.peaksInTenMinute.toString()
                startPostponedEnterTransition()
            }
        }
    }

}