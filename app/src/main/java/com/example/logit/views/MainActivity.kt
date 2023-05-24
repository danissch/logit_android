package com.example.logit.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logit.utils.Adapter
import com.example.logit.R
import com.example.logit.data.DataHandler
import com.example.logit.data.DataObject
import com.example.logit.utils.ActivityHelper
//import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private lateinit var add: Button
    private lateinit var deleteAll: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            DataHandler.getAllData(this@MainActivity)
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
        ActivityHelper.setTopBarConfiguration(this,true, true)

    }

    fun setRecycler(){
        recyclerView.adapter = Adapter(DataObject.getAllData())
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun confirmDelete() {
        val newFragment = DialogConfirm("¿Seguro que quieres borrar todas las tareas?", "BORRAR", "CANCELAR")
        newFragment.listenerPositive = {
            DataHandler.deleteAll(this)
            setRecycler()
        }
        newFragment.listenerNegative = {}
        newFragment.show(supportFragmentManager, "delete")
    }
}