package com.neurotech.stressapp.ui.markupupdate

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.neurotech.domain.Codes
import com.neurotech.domain.models.MarkupDomainModel
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentUpdateMarkupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DayMarkupUpdateFragment : Fragment(R.layout.fragment_update_markup) {

    @Inject
    lateinit var factory: DayMarkupUpdateFactory

    private var _binder: FragmentUpdateMarkupBinding? = null
    private val binder get() = _binder!!
    private var focusFlag = false
    val viewModel by lazy { ViewModelProvider(this, factory)[DayMarkupUpdateViewModel::class.java] }
    private var _markup: MarkupDomainModel? = null
    val markup get() = _markup!!

    companion object {
        const val bundleKey = "MARKUP_FRAGMENT_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        _binder = FragmentUpdateMarkupBinding.inflate(inflater, container, false)
        return binder.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _markup = arguments?.getSerializable(bundleKey) as MarkupDomainModel
        binder.updateFragmentTitle.text = markup.markupName
        binder.beginTimeTextView.text = markup.timeBegin ?: "00:00"
        binder.endTimeTextView.text = markup.timeEnd ?: "00:00"
        initTimeSettings()
        fillSpinners()
        binder.updateButton.setOnClickListener {
            markup.timeBegin = binder.beginTimeTextView.text.toString()
            markup.timeEnd = binder.endTimeTextView.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val updateCode = viewModel.updateMarkup(markup)
                if(updateCode == Codes.markupUpdate){
                    launch(Dispatchers.Main) {
                        findNavController().navigate(R.id.action_dayMarkupUpdateFragment_to_settingsFragment)
                    }
                }else{
                    launch(Dispatchers.Main) {
                        Toast.makeText(context,updateCode,Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

    }

    private fun fillSpinners() {
        viewModel.sources.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binder.firstSourceSpinner.adapter = adapter
            binder.secondSourceSpinner.adapter = adapter
            binder.firstSourceSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
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

            binder.secondSourceSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
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
    private fun initTimeSettings() {
        binder.beginTimeTextView.background =
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.selected_background
            )
        binder.beginTimeTextView.setOnClickListener {
            it.background =
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.selected_background
                )
            focusFlag = false
            binder.endTimeTextView.setBackgroundColor(Color.WHITE)
        }

        binder.endTimeTextView.setOnClickListener {
            it.background =
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.selected_background
                )
            focusFlag = true
            binder.beginTimeTextView.setBackgroundColor(Color.WHITE)
        }

        binder.timeSpinner.setIs24HourView(true)
        binder.timeSpinner.setOnTimeChangedListener { _, hourOfDay, minuteOfHour ->
            val minute: String = if (minuteOfHour < 10) {
                "0$minuteOfHour"
            } else "$minuteOfHour"

            val hour: String = if (hourOfDay < 10) {
                "0$hourOfDay"
            } else "$hourOfDay"
            if (!focusFlag) {
                binder.beginTimeTextView.text = "$hour:$minute"
            } else {
                binder.endTimeTextView.text = "$hour:$minute"
            }
        }
    }
}