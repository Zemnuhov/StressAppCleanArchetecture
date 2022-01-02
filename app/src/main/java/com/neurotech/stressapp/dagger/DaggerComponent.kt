package com.neurotech.stressapp.dagger

import com.neurotech.stressapp.data.BleService
import com.neurotech.stressapp.data.ConnectionRepositoryImpl
import com.neurotech.stressapp.domain.repository.MainFunctions
import com.neurotech.stressapp.domain.usecases.connection.GetListDeviceUseCase
import com.neurotech.stressapp.ui.viewmodel.SearchFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DaggerModule::class])
interface DaggerComponent {
    fun inject(bleService: BleService)
    fun inject(mainFunctions: MainFunctions)
    fun inject(searchFragmentViewModel: SearchFragmentViewModel)
    fun inject(getListDeviceUseCase: GetListDeviceUseCase)
    fun inject(connectionRepositoryImpl: ConnectionRepositoryImpl)
}