package com.example.logit.utils
//import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity



object ActivityHelper {

    fun setTopBarConfiguration(context:AppCompatActivity, hideNavigationBar: Boolean = false, hideActionBar: Boolean = false){
        context.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        //context.actionBar?.hide()

        if (hideActionBar){
            context.getSupportActionBar()?.hide()
        }

        if (Build.VERSION.SDK_INT < 16) {
            context.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        if (hideNavigationBar) {
            fullScreenCall(context)
            context.window.decorView.apply {
                // Hide both the navigation bar and the status bar.
                // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
                // a general rule, you should design your app to hide the status bar whenever you
                // hide the navigation bar.
                systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
            }
        }
    }

    fun setupBackButton(context: AppCompatActivity){
        context.getActionBar()?.setDisplayHomeAsUpEnabled(true);
    }

    fun fullScreenCall(context: AppCompatActivity) {
        context?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        //context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    }

}