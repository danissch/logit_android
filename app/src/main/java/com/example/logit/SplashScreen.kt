package com.example.logit

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.room.Room
import com.example.logit.data.DataObject
import com.example.logit.data.MyDatabase
import com.example.logit.utils.ActivityHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashScreen: AppCompatActivity() {

    private lateinit var database: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        database = Room.databaseBuilder(applicationContext, MyDatabase::class.java, "logit").build()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 5000)

    }

    override fun onStart() {
        super.onStart()
        ActivityHelper.setTopBarConfiguration(this, true, true)
    }

}