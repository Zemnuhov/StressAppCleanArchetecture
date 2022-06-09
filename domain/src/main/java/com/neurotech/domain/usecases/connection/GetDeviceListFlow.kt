package com.neurotech.domain.usecases.connection

import com.neurotech.domain.models.DeviceDomainModel
import com.neurotech.domain.repository.ConnectionRepository
import kotlinx.coroutines.flow.Flow

class GetDeviceListFlow(val repository: ConnectionRepository) {
    suspend operator fun invoke(): Flow<List<DeviceDomainModel>> {
        return repository.getDeviceListFlow()
    }
}