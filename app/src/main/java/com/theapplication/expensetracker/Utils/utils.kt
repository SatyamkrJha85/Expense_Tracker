package com.theapplication.expensetracker.Utils

import java.text.SimpleDateFormat
import java.util.Locale

object utils {

    fun formatdate(dateinmillis:Long):String{
        val dateFormatter = SimpleDateFormat("dd/MM/YY",Locale.getDefault())
        return dateFormatter.format(dateinmillis)
    }
}