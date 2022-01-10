package com.neurotech.stressapp.ui

import android.bluetooth.BluetoothGatt
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
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.BleConnection
import com.neurotech.stressapp.ui.adapter.SearchCardAdapter
import com.neurotech.stressapp.ui.viewmodel.SearchFragmentViewModel
import com.polidea.rxandroidble2.RxBleConnection

class SearchFragment : Fragment() {

    private lateinit var searchView: View

    companion object {
        lateinit var viewModel: SearchFragmentViewModel
    }

    private lateinit var swipeRefresher: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchView = inflater.inflate(R.layout.search_fragment, container, false)
        viewModel = ViewModelProvider(this)[SearchFragmentViewModel::class.java]
        initView()
        setObservers()
        setListeners()
        return searchView
    }

    private fun initView() {
        (context as MainActivity).appMenu.findItem(R.id.menu_search).isVisible = true
        (context as MainActivity).appMenu.findItem(R.id.disconnect_device).isVisible = false
        swipeRefresher = searchView.findViewById(R.id.refresh_list_device)
        recyclerView = searchView.findViewById(R.id.recycler_view_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SearchCardAdapter(listOf())
        progressBar = searchView.findViewById(R.id.connect_progress)
        progressBar.visibility = View.GONE
    }

    private fun setListeners() {
        swipeRefresher.setOnRefreshListener {
            viewModel.searchDevice()
        }

        (context as MainActivity).appMenu.findItem(R.id.menu_search).setOnMenuItemClickListener {
            viewModel.searchDevice()
            return@setOnMenuItemClickListener false
        }
    }

    private fun setObservers() {
        viewModel.searchState.observe(viewLifecycleOwner) {
            swipeRefresher.isRefreshing = it
        }
        viewModel.deviceList.observe(viewLifecycleOwner) {
            recyclerView.adapter = SearchCardAdapter(it)
        }
        viewModel.deviceState.observe(viewLifecycleOwner) {
            Log.e("DS", it.toString())
            when (it) {
                BleConnection.CONNECTING -> progressBar.visibility = View.VISIBLE
                BleConnection.CONNECTED -> {
                    progressBar.visibility = View.GONE
                    Singleton.showFragment(MainFragment(), "base")
                    Singleton.fragmentManager.beginTransaction().remove(this).commit()
                }
                else -> progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel
    }
}
