package com.neurotech.stressapp.dagger

import com.neurotech.stressapp.data.DataFlowAnalyzer
import com.neurotech.stressapp.data.ble.BleService
import com.neurotech.stressapp.data.implementations.*
import com.neurotech.stressapp.ui.MainActivity
import com.neurotech.stressapp.ui.fragment.StartFragment
import com.neurotech.stressapp.ui.viewmodel.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DaggerModule::class, DaggerRepositoryModule::class])
interface DaggerComponent {
    fun inject(activity: MainActivity)
    fun inject(viewModel: SearchFragmentViewModel)
    fun inject(viewModel: MainFragmentViewModel)
    fun inject(viewModel: GraphFragmentViewModel)
    fun inject(viewModel: TonicItemFragmentViewModel)
    fun inject(viewModel: PhaseItemFragmentViewModel)
    fun inject(viewModel: StatisticItemFragmentViewModel)
    fun inject(viewModel: SourceViewModel)
    fun inject(repository: ConnectionRepositoryImpl)
    fun inject(repository: MainFunctionsImpl)
    fun inject(repository: GraphRepositoryImpl)
    fun inject(repository: TonicCaseRepositoryImpl)
    fun inject(repository: StatisticCaseRepositoryImpl)
    fun inject(repository: PhaseCaseRepositoryImpl)
    fun inject(repository: MarkupCaseRepositoryImpl)
    fun inject(service: BleService)
    fun inject(dataFlowAnalyzer: DataFlowAnalyzer)
    fun inject(startFragment: StartFragment)
    fun inject(viewModel: DayMarkupViewModel)
}