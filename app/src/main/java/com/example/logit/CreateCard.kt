package com.example.logit

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.room.Room
import com.example.logit.data.DataObject
import com.example.logit.data.MyDatabase
import com.example.logit.models.Entity
import com.example.logit.utils.ActivityHelper
import com.example.logit.utils.SpinnerStatusHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CreateCard: AppCompatActivity() {

    private lateinit var database: MyDatabase
    private lateinit var save_button: Button
    private lateinit var create_title: EditText
    private lateinit var create_description: EditText
    private lateinit var spinnerStatus:Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        database = Room.databaseBuilder(applicationContext, MyDatabase::class.java, "logit").build()

        save_button = findViewById(R.id.save_button)
        create_title = findViewById(R.id.create_title)
        create_description = findViewById(R.id.create_description)
        spinnerStatus = findViewById(R.id.create_status)

        val spinnerHandler = SpinnerStatusHandler(this,spinnerStatus)
        spinnerHandler.configure()

        save_button.setOnClickListener {

            if (create_title.text.toString().trim { it <= ' '}.isNotEmpty()
                && create_description.text.toString().trim { it <= ' '}.isNotEmpty()
                && spinnerHandler.selectedStatus != 0
            ){
                var title = create_title.getText().toString()
                var description = create_description.getText().toString()
                var status = spinnerStatus.selectedItem.toString()

                DataObject.setData(0, title, description, status)
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title, description, status))
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}