package com.example.logit.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.logit.utils.Adapter
import com.example.logit.R
import com.example.logit.data.DataObject
import com.example.logit.data.MyDatabase
import com.example.logit.utils.ActivityHelper
//import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var database: MyDatabase
    private lateinit var add: Button
    private lateinit var deleteAll: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("appflow:: MainActivity: onCreate")

        setContentView(R.layout.activity_main)

        database = Room.databaseBuilder(applicationContext, MyDatabase::class.java, "logit").build()

        GlobalScope.launch {
            DataObject.listData = database.dao().getTasks() as MutableList<CardInfo>
            setRecycler()
        }

        add = findViewById(R.id.add)
        deleteAll = findViewById(R.id.deleteAll)
        recyclerView = findViewById(R.id.recycler_view)



        add.setOnClickListener {
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)
        }

        deleteAll.setOnClickListener {
            confirmDelete()
        }
    }

    override fun onStart() {
        super.onStart()
        println("appflow:: MainActivity: onStart")
        ActivityHelper.setTopBarConfiguration(this,true, true)
    }

    override fun onResume() {
        super.onResume()
        println("appflow:: MainActivity: onResume")
    }

    override fun onPause() {
        super.onPause()
        println("appflow:: MainActivity: onPause")
    }

    override fun onStop() {
        super.onStop()
        println("appflow:: MainActivity: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("appflow:: MainActivity: onDestroy")
    }

    fun setRecycler(){
        recyclerView.adapter = Adapter(DataObject.getAllData())
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun confirmDelete() {
        val newFragment = DialogConfirm("¿Seguro que quieres borrar todas las tareas?", "BORRAR", "CANCELAR")
        newFragment.listenerPositive = {
            DataObject.deleteAll()
            GlobalScope.launch {
                database.dao().deleteAll()
            }
            setRecycler()
        }
        newFragment.listenerNegative = {}
        newFragment.show(supportFragmentManager, "delete")
    }
}