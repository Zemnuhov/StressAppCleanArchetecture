package com.neurotech.domain.usecases.connection

import com.neurotech.domain.repository.ConnectionRepository

class ConnectionToPeripheral(val repository: ConnectionRepository) {
    suspend operator fun invoke(MAC: String){
        repository.connectionToPeripheral(MAC)
    }
}