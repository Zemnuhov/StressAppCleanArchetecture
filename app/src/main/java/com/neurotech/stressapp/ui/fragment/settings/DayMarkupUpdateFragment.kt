package com.neurotech.stressapp.ui.fragment.settings

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationBarView
import com.neurotech.stressapp.R
import com.neurotech.stressapp.data.database.entity.MarkupEntity
import com.neurotech.stressapp.databinding.FragmentUpdateMarkupBinding
import com.neurotech.stressapp.ui.viewmodel.DayMarkupUpdateViewModel

class DayMarkupUpdateFragment: Fragment(R.layout.fragment_update_markup) {

    private var _binder: FragmentUpdateMarkupBinding? = null
    private val binder get() = _binder!!
    private var focusFlag = false
    val  viewModel by lazy { ViewModelProvider(this)[DayMarkupUpdateViewModel::class.java] }
    private var _markup: MarkupEntity? = null
    val markup get() = _markup!!

    companion object{
        const val bundleKey = "MARKUP_FRAGMENT_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binder = FragmentUpdateMarkupBinding.inflate(inflater,container,false)
        return binder.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("Bundle",arguments?.getSerializable(bundleKey).toString())
        _markup = arguments?.getSerializable(bundleKey) as MarkupEntity
        binder.updateFragmentTitle.text = markup.markupName
        binder.beginTimeTextView.text = markup.time?.split("-")?.get(0) ?: "00:00"
        binder.endTimeTextView.text = markup.time?.split("-")?.get(1) ?: "00:00"
        initTimeSettings()
        fillSpinners()
        binder.updateButton.setOnClickListener {
            markup.time = "${binder.beginTimeTextView.text}-${binder.endTimeTextView.text}"
            viewModel.updateMarkup(markup)
            findNavController().navigate(R.id.action_dayMarkupUpdateFragment_to_settingsFragment)
        }

    }

    private fun fillSpinners(){
        viewModel.sources.observe(viewLifecycleOwner){
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,it)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binder.firstSourceSpinner.adapter = adapter
            binder.secondSourceSpinner.adapter = adapter
            binder.firstSourceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    markup.firstSource = it[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            binder.secondSourceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    markup.secondSource = it[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initTimeSettings(){
        binder.beginTimeTextView.setBackgroundColor(Color.GRAY)
        binder.beginTimeTextView.setOnClickListener {
            it.setBackgroundColor(Color.GRAY)
            focusFlag = false
            binder.endTimeTextView.setBackgroundColor(Color.WHITE)
        }

        binder.endTimeTextView.setOnClickListener {
            it.setBackgroundColor(Color.GRAY)
            focusFlag = true
            binder.beginTimeTextView.setBackgroundColor(Color.WHITE)
        }

        binder.timeSpiner.setIs24HourView(true)
        binder.timeSpiner.setOnTimeChangedListener { _, hourOfDay, minute ->
            val minuteResult: String = if(minute < 10) {
                "0$minute"
            } else "$minute"
            if(!focusFlag){
                binder.beginTimeTextView.text = "$hourOfDay:$minuteResult"
            }else{
                binder.endTimeTextView.text = "$hourOfDay:$minuteResult"
            }
        }
    }
}