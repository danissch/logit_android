package com.example.logit.data

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.logit.models.Entity
import com.example.logit.views.CardInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object DataHandler {

    val saveKey = "saveLocally"

    fun setupStorageBehavior(context: AppCompatActivity, storageLocally: Boolean){
        val sharedPref = context.getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(saveKey, storageLocally)
            apply()
        }
    }

    suspend fun getAllData(activity: AppCompatActivity){
        val database: MyDatabase = Room.databaseBuilder(activity.applicationContext, MyDatabase::class.java, "logit").build()
        DataObject.listData = database.dao().getTasks() as MutableList<CardInfo>
    }

    fun saveData(activity: AppCompatActivity, title:String, description:String, status:String) {
        DataObject.setData(0, title, description, status)
            GlobalScope.launch {
                val database: MyDatabase = Room.databaseBuilder(activity.applicationContext, MyDatabase::class.java, "logit").build()
                database.dao().insertTask(Entity(0, title, description, status))
            }
    }

    fun updateData(activity: AppCompatActivity, pos: Int, title: String, description: String, status: String, entity:Entity){
        DataObject.updateData(
            pos,
            title,
            description,
            status
        )
        GlobalScope.launch {
            val database: MyDatabase = Room.databaseBuilder(activity.applicationContext, MyDatabase::class.java, "logit").build()
            database.dao().updateTask(
                entity
            )
        }
    }

    fun deleteAll(activity: AppCompatActivity){
        DataObject.deleteAll()
        GlobalScope.launch {
            val database: MyDatabase = Room.databaseBuilder(activity.applicationContext, MyDatabase::class.java, "logit").build()
            database.dao().deleteAll()
        }
    }

    fun delete(activity: AppCompatActivity, pos:Int, entity: Entity){
        DataObject.deleteData(pos)
        GlobalScope.launch {
            val database: MyDatabase = Room.databaseBuilder(activity.applicationContext, MyDatabase::class.java, "logit").build()
            database.dao().deleteTask(
                entity
            )
        }
    }

}