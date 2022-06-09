package com.neurotech.stressapp.service

import com.neurotech.stressapp.service.AppService
import kotlinx.coroutines.flow.Flow

interface Service {
    fun bindService()
    fun unbindService()
    fun getService(): AppService?
    fun getBoundStateService(): Flow<Boolean>
}