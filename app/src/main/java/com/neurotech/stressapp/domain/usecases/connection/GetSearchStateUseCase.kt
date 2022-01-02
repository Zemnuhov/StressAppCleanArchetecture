package com.neurotech.stressapp.domain.usecases.connection

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.ConnectionRepository

class GetSearchStateUseCase(private val connectionRepository: ConnectionRepository) {
    fun getSearchState(): LiveData<Boolean>{
        return connectionRepository.searchState()
    }
}