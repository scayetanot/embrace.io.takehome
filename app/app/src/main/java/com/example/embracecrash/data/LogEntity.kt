package com.example.embracecrash.data

import androidx.room.Entity

@Entity(tableName = "LogEntries", primaryKeys = ["timeStamp"])
data class LogEntity(
        var timeStamp: Long,
        var type: String,
        var description: String
)