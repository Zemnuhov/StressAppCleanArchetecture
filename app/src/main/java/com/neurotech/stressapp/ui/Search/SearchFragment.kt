package com.neurotech.stressapp.ui.Search

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentSearchBinding
import com.neurotech.stressapp.ui.MainActivity
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search), SearchCardAdapter.CallBack {

    @Inject
    lateinit var factory: SearchFragmentViewModelFactory
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    val viewModel by lazy { ViewModelProvider(this, factory)[SearchFragmentViewModel::class.java] }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_search ->{
                viewModel.searchDevice()
                true
            }
            else -> super.onContextItemSelected(item)
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menu_search).isVisible = true
        menu.findItem(R.id.disconnect_device).isVisible = false
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_search ->{
                viewModel.searchDevice()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as App).component.inject(this)
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        initView()
        setObservers()
        setListeners()
        requestPermissions()
    }

    private fun initView() {
        binding.recyclerViewList.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewList.adapter = SearchCardAdapter(listOf())
        binding.connectProgress.visibility = View.GONE
    }

    private fun setListeners() {
        binding.refreshListDevice.setOnRefreshListener {
            viewModel.searchDevice()
        }
    }

    private fun setObservers() {
        viewModel.searchState.observe(viewLifecycleOwner) {
            binding.refreshListDevice.isRefreshing = it
        }
        viewModel.deviceList.observe(viewLifecycleOwner) {
            val adapter = SearchCardAdapter(it)
            adapter.registerCallback(this)
            binding.recyclerViewList.adapter = adapter
        }
        viewModel.deviceState.observe(viewLifecycleOwner) {
            when (it) {
                "CONNECTING" -> binding.connectProgress.visibility = View.VISIBLE
                "CONNECTED" -> {
                    binding.connectProgress.visibility = View.GONE
                    findNavController().navigate(R.id.action_searchFragment_to_mainHostFragment,
                        bundleOf(),
                        navOptions {
                            this.anim {
                                enter = R.anim.nav_default_enter_anim
                                popEnter = R.anim.nav_default_pop_enter_anim
                                exit = R.anim.nav_default_exit_anim
                                popExit = R.anim.nav_default_pop_exit_anim
                            }
                        })
                    viewModel.stopScan()
                }
                else -> binding.connectProgress.visibility = View.GONE
            }
        }
    }

    override fun connectDeviceCallBack(MAC: String) {
        viewModel.connectionToDevice(MAC)
    }

    private fun requestPermissions(){
        val permissionFineLocationStatus = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        if(permissionFineLocationStatus == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        val permissionReadStorageStatus = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
        if(permissionReadStorageStatus == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }

        val permissionWriteStorageStatus = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(permissionWriteStorageStatus == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
