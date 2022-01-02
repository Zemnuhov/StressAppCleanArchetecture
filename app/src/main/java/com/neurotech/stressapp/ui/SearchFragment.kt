package com.neurotech.stressapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.neurotech.stressapp.R
import com.neurotech.stressapp.ui.viewmodel.SearchFragmentViewModel

class SearchFragment:Fragment() {

    lateinit var searchView: View
    companion object{
        lateinit var viewModel: SearchFragmentViewModel
    }

    lateinit var swipeRefresher: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchView = inflater.inflate(R.layout.search_fragment,container,false)
        viewModel = ViewModelProvider(this)[SearchFragmentViewModel::class.java]
        initView()
        setObservers()
        return searchView
    }

    private fun initView(){
        swipeRefresher = searchView.findViewById(R.id.refresh_list_device)
        recyclerView = searchView.findViewById(R.id.recycler_view_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SearchCardAdapter(listOf())
        progressBar = searchView.findViewById(R.id.connect_progress)
        progressBar.visibility = View.GONE
    }

    private fun setObservers(){
        swipeRefresher.setOnRefreshListener {
            viewModel.deviceList.observe(viewLifecycleOwner){
                recyclerView.adapter = SearchCardAdapter(it)
            }
        }
        viewModel.searchState.observe(viewLifecycleOwner){
            if(!it){
                progressBar.visibility = View.GONE
                swipeRefresher.isRefreshing = false

            }
        }
        viewModel.deviceState.observe(viewLifecycleOwner){
            Log.e("ConnectState",it.toString())
        }
    }
}