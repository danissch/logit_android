package com.example.logit.data

import androidx.room.*
import com.example.logit.CardInfo
import com.example.logit.models.Entity

@Dao
interface DAO {
    @Insert
    suspend fun insertTask(entity: Entity)

    @Update
    suspend fun updateTask(entity: Entity)

    @Delete
    suspend fun deleteTask(entity: Entity)

    @Query("Delete from logit")
    suspend fun deleteAll()

    @Query("SELECT * FROM logit ORDER BY id DESC")
    suspend fun getTasks():List<CardInfo>



}