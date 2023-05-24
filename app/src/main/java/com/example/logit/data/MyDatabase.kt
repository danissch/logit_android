package com.example.logit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.logit.models.Entity

@Database(entities = [Entity::class], version = 1, exportSchema = true)
abstract class MyDatabase: RoomDatabase() {
    abstract fun dao(): DAO
}