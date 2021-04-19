package com.example.embracecrash.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.embracecrash.data.LogEntity
import com.example.embracecrash.data.RoomDataConverter

@Database(entities = [LogEntity::class], version = 1)
@TypeConverters(RoomDataConverter::class)
abstract class LocalDataSourceRoomDb : RoomDatabase() {

    abstract fun localDataObject(): LocalDataSourceDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDataSourceRoomDb? = null

        fun getDatabase(context: Context): LocalDataSourceRoomDb {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, LocalDataSourceRoomDb::class.java, "weather_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}