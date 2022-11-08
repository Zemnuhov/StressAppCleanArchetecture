package com.neurotech.stressapp.service

import kotlinx.coroutines.flow.Flow

interface Service {
    fun bindService()
    fun unbindService()
    fun getService(): AppService?
    fun getBoundStateService(): Flow<Boolean>
}