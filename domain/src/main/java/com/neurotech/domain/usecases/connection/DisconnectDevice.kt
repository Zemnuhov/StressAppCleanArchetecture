package com.neurotech.domain.usecases.connection

import com.neurotech.domain.repository.ConnectionRepository

class DisconnectDevice (val repository: ConnectionRepository) {
    suspend operator fun invoke(){
        repository.disconnectDevice()
    }
}