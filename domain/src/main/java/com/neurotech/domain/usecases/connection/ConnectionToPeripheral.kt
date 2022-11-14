package com.neurotech.domain.usecases.connection

import com.neurotech.domain.repository.ConnectionRepository
import com.neurotech.domain.repository.SettingsRepository

class ConnectionToPeripheral(val connectionRepository: ConnectionRepository, val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(MAC: String){
        settingsRepository.saveDevice(MAC)
        connectionRepository.connectionToPeripheral(MAC)
    }
}