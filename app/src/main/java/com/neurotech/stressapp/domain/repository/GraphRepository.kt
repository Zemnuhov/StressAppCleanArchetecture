package com.neurotech.stressapp.domain.repository

import androidx.lifecycle.LiveData

interface GraphRepository {
    fun getTonicValue(): LiveData<HashMap<String,Any>>
    fun getPhaseValue(): LiveData<HashMap<String,Any>>
    fun getThreshold() : Double
}