package com.example.embracecrash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.embracecrash.data.LogEntity
import com.example.embracecrash.data.ResultLogs
import com.example.embracecrash.repository.AppRepositoryImpl
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val repositoryImpl: AppRepositoryImpl
) : ViewModel() {

    private var _resultLogs = MutableLiveData<List<LogEntity>>()
    var resultLogs: LiveData<List<LogEntity>> = _resultLogs

    private var _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    fun getLogs(){
        MainScope().launch {
            try{
                when(val response = repositoryImpl.getLogs()){
                    is ResultLogs.Success -> {
                        _resultLogs.postValue(response.data)
                    }
                }

            } catch (e: java.lang.Exception) {
                _errorMessage.postValue(e.message)
            }
        }
    }

    fun getCrashLogs(){
        MainScope().launch {
            try{
                when(val response = repositoryImpl.getCrashLogs()){
                    is ResultLogs.Success -> {
                        _resultLogs.postValue(response.data)
                    }
                }

            } catch (e: java.lang.Exception) {
                _errorMessage.postValue(e.message)
            }
        }
    }

    fun deleteAll() {
        MainScope().launch {
            try {
                repositoryImpl.deleteAll()
            } catch (e: java.lang.Exception) {
                _errorMessage.postValue(e.message)
            }
        }
    }
}
