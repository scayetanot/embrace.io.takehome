package com.example.embracecrash.repository

import com.example.embracecrash.data.LogEntity
import com.example.embracecrash.data.ResultLogs

interface AppRepository {
    suspend fun getLogs(): ResultLogs<List<LogEntity>>
    suspend fun getCrashLogs(): ResultLogs<List<LogEntity>>
    suspend fun addEntry(entry: LogEntity)
    suspend fun deleteAll()
}