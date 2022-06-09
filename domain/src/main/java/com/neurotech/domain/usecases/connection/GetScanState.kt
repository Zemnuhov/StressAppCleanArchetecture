package com.neurotech.domain.usecases.connection

import com.neurotech.domain.repository.ConnectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

class GetScanState(val repository: ConnectionRepository) {
    suspend operator fun invoke(): Flow<Boolean> {
        return repository.getScanState()
    }
}