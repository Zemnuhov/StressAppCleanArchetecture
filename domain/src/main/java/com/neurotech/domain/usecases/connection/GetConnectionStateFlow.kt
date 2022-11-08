package com.neurotech.domain.usecases.connection

import com.neurotech.domain.repository.ConnectionRepository
import kotlinx.coroutines.flow.Flow

class GetConnectionStateFlow(val repository: ConnectionRepository) {
    suspend operator fun invoke(): Flow<String> {
        return repository.getConnectionStateFlow()
    }
}