package com.neurotech.stressapp.ui.fragment.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.neurotech.stressapp.data.database.entity.MarkupEntity
import com.neurotech.stressapp.databinding.FragmentDayMarkupBinding
import com.neurotech.stressapp.ui.adapter.DayMarkupAdapter
import com.neurotech.stressapp.ui.viewmodel.DayMarkupViewModel

class DayMarkupFragment: Fragment(), DayMarkupAdapter.DayMarkupCallback {

    private var _binder: FragmentDayMarkupBinding? = null
    private val binder get() = _binder!!
    val viewModel by lazy { ViewModelProvider(this)[DayMarkupViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binder = FragmentDayMarkupBinding.inflate(inflater,container,false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binder.dayMarkupRecyclerView.layoutManager = LinearLayoutManager(context)
        binder.dayMarkupRecyclerView.adapter = DayMarkupAdapter(listOf())
        viewModel.markupList.observe(viewLifecycleOwner){
            val adapter = DayMarkupAdapter(it)
            adapter.dayMarkupCallback = this
            binder.dayMarkupRecyclerView.adapter = adapter
        }
        binder.button.setOnClickListener {
            viewModel.addMarkup(binder.editText.text.toString())
        }

    }

    override fun deleteMarkup(markup: MarkupEntity) {
        viewModel.deleteMarkup(markup)
    }
}