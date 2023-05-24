package com.example.logit.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.example.logit.R
import com.example.logit.data.DataHandler
import com.example.logit.data.DataObject
import com.example.logit.data.MyDatabase
import com.example.logit.utils.ActivityHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class SplashScreen: AppCompatActivity() {

    private lateinit var database: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        database = Room.databaseBuilder(applicationContext, MyDatabase::class.java, "logit").build()
    }

    override fun onStart() {
        super.onStart()
        ActivityHelper.setTopBarConfiguration(this, true, true)
        confirmAcceptance()
    }

    fun confirmAcceptance() {
        val canSave = this?.getPreferences(Context.MODE_PRIVATE)?.getBoolean(DataHandler.saveKey,false)

        if (canSave!!) {
            gotoMainActivity()
        } else {
            val newFragment = DialogConfirm(
                "Esta aplicación guarda los datos de las tareas en tu dispositivo. \n\n¿Estás de acuerdo en almacenar los datos de las tareas en tu dispositivo? \n\n Acepta para continuar",
                "ACEPTAR",
                "CANCELAR"
            )
            newFragment.listenerPositive = {
                DataHandler.setupStorageBehavior(this,true)
                gotoMainActivity()
            }
            newFragment.listenerNegative = {
                finish()
            }
            newFragment.show(supportFragmentManager, "delete")
        }
    }

    fun gotoMainActivity(){
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 4000)
    }

    override fun onDestroy() {
        super.onDestroy()
        exitProcess(0)
    }
}