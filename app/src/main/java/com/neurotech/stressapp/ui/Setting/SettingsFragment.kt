package com.neurotech.stressapp.ui.Setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentSettingsBinding
import com.neurotech.stressapp.ui.Setting.DayMarkup.DayMarkupFragment
import com.neurotech.stressapp.ui.Setting.Source.SourceFragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction().replace(
            binding.sourceItemInSetting.id,
            SourceFragment()
        ).commit()

        childFragmentManager.beginTransaction().replace(
            binding.dayMarkupItemInSetting.id,
            DayMarkupFragment()
        ).commit()
    }
}