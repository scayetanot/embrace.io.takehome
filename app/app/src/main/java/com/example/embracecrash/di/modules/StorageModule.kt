package com.example.embracecrash.di.modules

import android.app.Application
import androidx.room.Room
import com.example.embracecrash.data_source.LocalDataSourceDao
import com.example.embracecrash.data_source.LocalDataSourceRoomDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule(private val application: Application) {

    private var dataSource: LocalDataSourceRoomDb =
        Room.databaseBuilder(application, LocalDataSourceRoomDb::class.java, "db").build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): LocalDataSourceRoomDb {
        return dataSource
    }

    @Singleton
    @Provides
    fun providesAppDao(demoDatabase: LocalDataSourceRoomDb): LocalDataSourceDao {
        return demoDatabase.localDataObject()
    }
}