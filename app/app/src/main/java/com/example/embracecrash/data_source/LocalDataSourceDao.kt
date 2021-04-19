package com.example.embracecrash.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.embracecrash.data.LogEntity

@Dao
interface LocalDataSourceDao {

    @Query("SELECT * FROM LogEntries WHERE type = :type LIMIT 1")
    suspend fun getAllEntryWithCrashType(type: String = "crash"): List<LogEntity>

    @Query("SELECT * FROM LogEntries")
    suspend fun getAllEntry(): List<LogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEntry(entity: LogEntity?)

    @Query("DELETE FROM LogEntries")
    suspend fun deleteAllEntries()

}