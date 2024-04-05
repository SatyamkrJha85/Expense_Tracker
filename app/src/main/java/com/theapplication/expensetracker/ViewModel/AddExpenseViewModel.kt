package com.theapplication.expensetracker.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.theapplication.expensetracker.Data.Dao.ExpenseDao
import com.theapplication.expensetracker.Data.ExpenseDatabase
import com.theapplication.expensetracker.Data.Model.ExpenseEntity

class AddExpenseViewModel(val dao: ExpenseDao) : ViewModel(){

    suspend fun addExpense(expenseEntity: ExpenseEntity):Boolean{
        try {
            dao.insertExpense(expenseEntity)
            return true
        }
        catch (ex:Throwable){
            return false
        }
    }
}

class AddExpenseViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            val dao = ExpenseDatabase.getDatabase(context).expenseDao()
            return AddExpenseViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}