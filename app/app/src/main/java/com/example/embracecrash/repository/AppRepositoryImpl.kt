package com.example.embracecrash.repository

import com.example.embracecrash.data.LogEntity
import com.example.embracecrash.data.ResultLogs
import com.example.embracecrash.data_source.LocalDataSourceDao
import com.example.embracecrash.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AppRepositoryImpl(
    private val localDataSource: LocalDataSourceDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AppRepository {

    override suspend fun getLogs(): ResultLogs<List<LogEntity>> =
        withContext(ioDispatcher) {
            ResultLogs.Success(localDataSource.getAllEntry())
        }

    override suspend fun getCrashLogs(): ResultLogs<List<LogEntity>> =
        withContext(ioDispatcher) {
            ResultLogs.Success(localDataSource.getAllEntry())
        }

    override suspend fun addEntry(entry: LogEntity){
        withContext(ioDispatcher) {
            ResultLogs.Success(localDataSource.getAllEntry())
        }
    }

    override suspend fun deleteAll(){
        withContext(ioDispatcher) {
            ResultLogs.Success(localDataSource.deleteAllEntries())
        }
    }

}