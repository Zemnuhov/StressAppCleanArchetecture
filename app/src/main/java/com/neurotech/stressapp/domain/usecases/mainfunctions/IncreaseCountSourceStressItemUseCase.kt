package com.neurotech.stressapp.domain.usecases.mainfunctions

import com.neurotech.stressapp.domain.SourceStressAndCountItem
import com.neurotech.stressapp.domain.repository.MainFunctions

class IncreaseCountSourceStressItemUseCase(private val mainFunctions: MainFunctions) {
    fun increaseCountSourceStressItem(sourceStressAndCountItem: SourceStressAndCountItem){
        return mainFunctions.increaseCountSourceStressItem(sourceStressAndCountItem)
    }
}