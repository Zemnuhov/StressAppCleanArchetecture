package com.neurotech.stressapp.ui.setting.DayMarkup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.neurotech.domain.models.MarkupDomainModel
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentDayMarkupBinding
import com.neurotech.stressapp.ui.markupupdate.DayMarkupUpdateFragment
import javax.inject.Inject

class DayMarkupFragment: Fragment(), DayMarkupAdapter.DayMarkupCallback {

    @Inject
    lateinit var factory: DayMarkupFragmentFactory

    private var _binder: FragmentDayMarkupBinding? = null
    private val binder get() = _binder!!
    val viewModel by lazy { ViewModelProvider(this, factory)[DayMarkupViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
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
            if(binder.editText.text.isNotEmpty()) {
                viewModel.addMarkup(binder.editText.text.toString())
                binder.editText.text.clear()
                binder.editText.clearFocus()
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

    }

    override fun deleteMarkup(markup: MarkupDomainModel) {
        viewModel.deleteMarkup(markup)
    }

    override fun showUpdateFragment(markup: MarkupDomainModel) {
        val bundle = Bundle()
        bundle.putSerializable(DayMarkupUpdateFragment.bundleKey,markup)
        findNavController().navigate(R.id.action_settingsFragment_to_dayMarkupUpdateFragment,bundle)
    }
}