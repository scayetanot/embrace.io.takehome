package com.example.embracecrash.di.component

import android.content.Context
import com.example.embracecrash.MainActivity
import com.example.embracecrash.data_source.LocalDataSourceDao
import com.example.embracecrash.data_source.LocalDataSourceRoomDb
import com.example.embracecrash.di.modules.AppModule
import com.example.embracecrash.di.modules.CoroutinesModule
import com.example.embracecrash.di.modules.RepositoryModule
import com.example.embracecrash.di.modules.StorageModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RepositoryModule::class,
        CoroutinesModule::class,
        StorageModule::class
    ]
)

interface AppComponents {
    fun context(): Context

    fun appDataObject(): LocalDataSourceDao

    fun appDataBase(): LocalDataSourceRoomDb

    fun inject(mainActivity: MainActivity)
}