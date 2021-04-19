package com.example.embracecrash.di.modules

import com.example.embracecrash.data_source.LocalDataSourceDao
import com.example.embracecrash.di.IoDispatcher
import com.example.embracecrash.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAppRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        localData: LocalDataSourceDao
    ): AppRepositoryImpl {
        return AppRepositoryImpl(localData, ioDispatcher)
    }
}