package com.neurotech.stressapp.ui.search

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesarferreira.tempo.Tempo
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.FragmentSearchBinding
import javax.inject.Inject


class SearchFragment : Fragment(R.layout.fragment_search), SearchCardAdapter.CallBack {

    @Inject
    lateinit var factory: SearchFragmentViewModelFactory
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val bluetoothAdapter by lazy {
        (requireContext().getSystemService(AppCompatActivity.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }
    private val location by lazy {
        (requireContext().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager)
    }
    val viewModel by lazy { ViewModelProvider(this, factory)[SearchFragmentViewModel::class.java] }

    private var backPressedCount = 0
    private var lastBackPressedTime = 0L

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                viewModel.searchDevice()
                true
            }
            else -> super.onContextItemSelected(item)
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menu_search).isVisible = true
        menu.findItem(R.id.menu_disconnect_device).isVisible = false
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
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
        requireActivity().onBackPressedDispatcher.addCallback {
            backPressedCount++
            if (Tempo.now.time - lastBackPressedTime < 2000) {
                if (backPressedCount == 2) {
                    requireActivity().finish()
                }
            } else {
                backPressedCount = 1
            }
            lastBackPressedTime = Tempo.now.time
            Toast.makeText(
                requireContext(),
                "Что бы выйти из приложения нажмите ещё раз",
                Toast.LENGTH_SHORT
            ).show()
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

    private fun requestPermissions() {
        val permissionsList = mutableListOf<String>()
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.BLUETOOTH
            ) == PackageManager.PERMISSION_DENIED
        ) {
            permissionsList.add(Manifest.permission.BLUETOOTH)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_DENIED
            ) {
                permissionsList.add(Manifest.permission.BLUETOOTH_CONNECT)
            }
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.BLUETOOTH_SCAN
                ) == PackageManager.PERMISSION_DENIED
            ) {
                permissionsList.add(Manifest.permission.BLUETOOTH_SCAN)
            }
        }

        if(permissionsList.isNotEmpty()){
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsList.toTypedArray(),
                1
            )
        }


        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if(it.resultCode != Activity.RESULT_OK){
                    Toast.makeText(requireContext(), "Блютуз не включен!", Toast.LENGTH_SHORT).show()
                }
            }
            resultLauncher.launch(enableBtIntent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if(!location.isLocationEnabled){
                Toast.makeText(requireContext(), "Требуется включить местоположение", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
