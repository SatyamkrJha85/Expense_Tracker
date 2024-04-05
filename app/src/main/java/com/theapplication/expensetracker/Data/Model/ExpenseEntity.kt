package com.theapplication.expensetracker.Data.Model

import androidx.compose.runtime.MutableState
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Expense_table")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    val title:String,
    val amount:Double,
    val date:Long,
    val category:String,
    val type:String
)