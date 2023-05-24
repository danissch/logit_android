package com.example.logit.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logit")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title:String,
    var description:String,
    var status:String
    )