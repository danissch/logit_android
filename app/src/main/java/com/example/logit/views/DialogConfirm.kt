package com.example.logit.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogConfirm(
    var message: String,
    var positiveButtonText: String,
    var negativeButtonText: String
) : DialogFragment() {

    var listenerPositive: (()->Unit)? = null
    var listenerNegative: (()->Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(message)
                .setPositiveButton(positiveButtonText, DialogInterface.OnClickListener { dialog, id ->
                    listenerPositive?.invoke()
                })
                .setNegativeButton(negativeButtonText, DialogInterface.OnClickListener { dialog, id ->
                    listenerNegative?.invoke()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}