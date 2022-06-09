package com.neurotech.domain.usecases.connection

import com.neurotech.domain.repository.ConnectionRepository

class GetConnectionState (val repository: ConnectionRepository) {
    suspend operator fun invoke(): String {
        return repository.getConnectionState()
    }
}