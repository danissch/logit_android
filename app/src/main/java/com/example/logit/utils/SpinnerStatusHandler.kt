package com.example.logit.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.logit.R

class SpinnerStatusHandler(private var context: Context, private var spinner:Spinner) {
    var selectedStatus = -1
    private var statusItems: Array<String> = context.resources.getStringArray(R.array.status_list)

    fun configure(){
        val adapter = getArrayAdapter()
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setOnItemSelectedListener(spinnerStatusListener)
    }

    private fun getArrayAdapter(): ArrayAdapter<String> {
        val adapter = object : ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, statusItems) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                if(position == 0) {
                    view.setTextColor(Color.GRAY)
                } else {
                    //view.setTextColor(Color.BLACK)
                    //view.backgroundTintMode = PorterDuff.Mode.ADD
                }
                return view
            }
        }
        return adapter
    }

    private val spinnerStatusListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedStatus = position
                val value = parent!!.getItemAtPosition(position).toString()
                if(value == statusItems[0]){
                    (view as TextView).setTextColor(Color.GRAY)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


    fun getPositionFromStatus(status: String): Int{
        statusItems.forEachIndexed{ index, element ->
            if (element == status){
                return index
            }
        }
        return 0
    }
}