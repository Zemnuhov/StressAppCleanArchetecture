package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.repository.ResultDataRepository
import java.util.*

class SetKeepByTime(private val repository: ResultDataRepository) {
    suspend operator fun invoke(keep: String?, time: Date){
        repository.setKeepByTime(keep, time)
    }
}