package com.neurotech.stressapp.domain.repository

import androidx.lifecycle.LiveData

interface SourceCaseRepository {
    fun addSource(source: String): String
    fun deleteSource(source: String)
    fun getSourceList():LiveData<List<String>>
}