package com.neurotech.stressapp.dagger

import com.neurotech.stressapp.data.ble.BleService
import com.neurotech.stressapp.data.implementations.ConnectionRepositoryImpl
import com.neurotech.stressapp.data.implementations.GraphRepositoryImpl
import com.neurotech.stressapp.data.implementations.MainFunctionsImpl
import com.neurotech.stressapp.data.implementations.TonicCaseRepositoryImpl
import com.neurotech.stressapp.ui.MainActivity
import com.neurotech.stressapp.ui.viewmodel.GraphFragmentViewModel
import com.neurotech.stressapp.ui.viewmodel.MainFragmentViewModel
import com.neurotech.stressapp.ui.viewmodel.SearchFragmentViewModel
import com.neurotech.stressapp.ui.viewmodel.TonicItemFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DaggerModule::class, DaggerRepositoryModule::class])
interface DaggerComponent {
    fun inject(bleService: BleService)
    fun inject(viewModel: SearchFragmentViewModel)
    fun inject(viewModel: MainFragmentViewModel)
    fun inject(connectionRepositoryImpl: ConnectionRepositoryImpl)
    fun inject(mainActivity: MainActivity)
    fun inject(mainFunctionsImpl: MainFunctionsImpl)
    fun inject(graphRepositoryImpl: GraphRepositoryImpl)
    fun inject(tonicCaseRepositoryImpl: TonicCaseRepositoryImpl)
    fun inject(viewModel: GraphFragmentViewModel)
    fun inject(viewModel: TonicItemFragmentViewModel)
}