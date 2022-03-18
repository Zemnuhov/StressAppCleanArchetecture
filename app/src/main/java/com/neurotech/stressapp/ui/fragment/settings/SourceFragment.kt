package com.neurotech.stressapp.ui.fragment.settings

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.neurotech.stressapp.databinding.FragmentSourcesBinding
import com.neurotech.stressapp.ui.adapter.SourceAdapter
import com.neurotech.stressapp.ui.viewmodel.SourceViewModel


class SourceFragment : Fragment(), SourceAdapter.DeleteCallback {

    private var _binding: FragmentSourcesBinding? = null
    private val binding get() = _binding!!
    val viewModel by lazy { ViewModelProvider(this)[SourceViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSourcesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sourcesLayout.layoutManager = LinearLayoutManager(context)
        binding.sourcesLayout.adapter = SourceAdapter(listOf())
        viewModel.sourcesList.observe(viewLifecycleOwner){
            val adapter = SourceAdapter(it)
            adapter.callback = this
            binding.sourcesLayout.adapter = adapter
        }

        binding.button.setOnClickListener{
            val source = binding.editText.text.toString()
            Toast.makeText(requireContext(), viewModel.addSource(source).toString(), Toast.LENGTH_SHORT).show()
            binding.editText.setText("")
            binding.editText.clearFocus()
            val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun deleteSource(source: String) {
        viewModel.deleteSource(source)
    }
}