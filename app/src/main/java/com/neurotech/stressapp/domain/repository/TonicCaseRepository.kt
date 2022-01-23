package com.neurotech.stressapp.domain.repository

import androidx.lifecycle.LiveData

interface TonicCaseRepository {
    fun getAvgTonicValue():LiveData<Int>
    fun getTonicValue():LiveData<Int>
    fun setInterval(interval:String)
}