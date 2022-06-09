package com.neurotech.stressapp.di

import com.neurotech.domain.repository.MarkupRepository
import com.neurotech.domain.usecases.markupdata.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MarkupDataUseCaseModule {

    @Provides
    @Singleton
    fun provideAddMarkup(repository: MarkupRepository): AddMarkup{
        return AddMarkup(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteMarkup(repository: MarkupRepository): DeleteMarkup{
        return DeleteMarkup(repository)
    }

    @Provides
    @Singleton
    fun provideGetMarkupListFlow(repository: MarkupRepository): GetMarkupListFlow{
        return GetMarkupListFlow(repository)
    }

    @Provides
    @Singleton
    fun provideGetMarkupList(repository: MarkupRepository): GetMarkupList {
        return GetMarkupList(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateMarkup(repository: MarkupRepository): UpdateMarkup{
        return UpdateMarkup(repository)
    }

}