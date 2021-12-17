package com.neurotech.stressapp.domain.usecases

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.SourceStressAndCountItem
import com.neurotech.stressapp.domain.repository.MainFunctions

class GetSourceStressAndCountItemListUseCase(private val mainFunctions: MainFunctions) {
    fun getSourceStressAndCountItemList():LiveData<List<SourceStressAndCountItem>>{
        return mainFunctions.getSourceStressAndCountItemList()
    }
}