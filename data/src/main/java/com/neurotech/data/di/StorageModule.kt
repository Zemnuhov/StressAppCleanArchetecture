package com.neurotech.data.di

import com.neurotech.data.modules.storage.MarkupStorage
import com.neurotech.data.modules.storage.PeakStorage
import com.neurotech.data.modules.storage.ResultStorage
import com.neurotech.data.modules.storage.TonicStorage
import com.neurotech.data.modules.storage.database.MarkupDataBase
import com.neurotech.data.modules.storage.database.PeakDataBase
import com.neurotech.data.modules.storage.database.ResultDataBase
import com.neurotech.data.modules.storage.database.TonicDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideMarkupStorage(): MarkupStorage{
        return MarkupDataBase()
    }

    @Provides
    @Singleton
    fun providePeakStorage(): PeakStorage {
        return PeakDataBase()
    }

    @Provides
    @Singleton
    fun provideResultStorage(): ResultStorage {
        return ResultDataBase()
    }

    @Provides
    @Singleton
    fun provideTonicStorage(): TonicStorage {
        return TonicDataBase()
    }

}