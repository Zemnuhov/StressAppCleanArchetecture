package com.neurotech.stressapp.di

import com.neurotech.domain.repository.ResultDataRepository
import com.neurotech.domain.repository.UserRepository
import com.neurotech.domain.usecases.resultdata.GetResultsInOneHour
import com.neurotech.domain.usecases.user.GetUser
import com.neurotech.domain.usecases.user.SetDateOfBirth
import com.neurotech.domain.usecases.user.SetGender
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UserUseCaseModule {

    @Provides
    @Singleton
    fun providesGetUser(repository: UserRepository): GetUser {
        return GetUser(repository)
    }

    @Provides
    @Singleton
    fun providesSetDateOfBirth(repository: UserRepository): SetDateOfBirth {
        return SetDateOfBirth(repository)
    }

    @Provides
    @Singleton
    fun providesSetGender(repository: UserRepository): SetGender {
        return SetGender(repository)
    }


}