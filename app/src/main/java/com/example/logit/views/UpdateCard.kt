package com.example.logit.views

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.logit.R
import com.example.logit.data.DataObject
import com.example.logit.data.MyDatabase
import com.example.logit.models.Entity
import com.example.logit.utils.SpinnerStatusHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateCard: AppCompatActivity() {

    private lateinit var database: MyDatabase
    private lateinit var create_id: TextView
    private lateinit var create_title: EditText
    private lateinit var create_description: EditText
    private lateinit var delete_button: Button
    private lateinit var update_button: Button
    private lateinit var spinnerStatus:Spinner
    private lateinit var spinnerHandler: SpinnerStatusHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_update_card)

        database = Room.databaseBuilder(applicationContext, MyDatabase::class.java, "logit").build()

        create_id = findViewById(R.id.create_id)
        create_title = findViewById(R.id.create_title)
        create_description = findViewById(R.id.create_description)
        delete_button = findViewById(R.id.delete_button)
        update_button = findViewById(R.id.update_button)
        spinnerStatus = findViewById(R.id.create_status)

        spinnerHandler = SpinnerStatusHandler(this,spinnerStatus)
        spinnerHandler.configure()

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val id = DataObject.getData(pos).id
            val title = DataObject.getData(pos).title
            val description = DataObject.getData(pos).description
            val status = DataObject.getData(pos).status
            val selectedPosition = spinnerHandler.getPositionFromStatus(status)

            create_id.text = id.toString()
            create_title.setText(title)
            create_description.setText(description)
            spinnerStatus.setSelection(selectedPosition)

            delete_button.setOnClickListener {
                confirmDelete(pos)
            }

            update_button.setOnClickListener {
                update(pos)
            }
        }
    }

    fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun confirmDelete(pos: Int) {
        val newFragment = DialogConfirm("¿Seguro que quieres borrar esta tarea?", "BORRAR", "CANCELAR")
        newFragment.listenerPositive = {
            DataObject.deleteData(pos)
            GlobalScope.launch {
                database.dao().deleteTask(
                    getEntity()
                )
            }
            myIntent()
        }
        newFragment.listenerNegative = {}
        newFragment.show(supportFragmentManager, "delete")
    }

    fun update(pos: Int){
        if (create_title.text.toString().trim { it <= ' '}.isNotEmpty()
            && create_description.text.toString().trim { it <= ' '}.isNotEmpty()
            && spinnerHandler.selectedStatus != 0
        ){
            DataObject.updateData(
                pos,
                create_title.text.toString(),
                create_description.text.toString(),
                spinnerStatus.selectedItem.toString()
            )

            GlobalScope.launch {
                database.dao().updateTask(
                    getEntity()
                )
            }
            myIntent()
        }
    }


    private fun getEntity():Entity{
        return Entity(
            create_id.text.toString().toInt(),//pos + 1,
            create_title.text.toString(),
            create_description.text.toString(),
            spinnerStatus.selectedItem.toString()
        )
    }
}