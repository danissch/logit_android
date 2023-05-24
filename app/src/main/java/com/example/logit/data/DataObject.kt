package com.example.logit.data

import com.example.logit.CardInfo

object DataObject {
    var listData = mutableListOf<CardInfo>()

    fun setData(id: Int, title:String, description: String, status: String){
        listData.add(CardInfo(id, title, description, status))
    }

    fun getAllData(): List<CardInfo>{
        return listData
    }

    fun deleteAll(){
        listData.clear()
    }

    fun getData(pos:Int): CardInfo {
        return listData[pos]
    }

    fun deleteData(pos:Int){
        listData.removeAt(pos)
    }

    fun updateData(pos:Int, title:String, description: String, status:String){
        listData[pos].title = title
        listData[pos].description = description
        listData[pos].status = status
    }

}