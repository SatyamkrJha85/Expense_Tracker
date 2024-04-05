package com.theapplication.expensetracker.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Dao
import com.theapplication.expensetracker.Data.Dao.ExpenseDao
import com.theapplication.expensetracker.Data.ExpenseDatabase
import com.theapplication.expensetracker.Data.Model.ExpenseEntity
import com.theapplication.expensetracker.R
import kotlinx.coroutines.launch

class HomeViewModel(val dao: ExpenseDao):ViewModel() {
    val expenses = dao.gelAllExpense()


    suspend fun deleteExpense(expenseEntity: ExpenseEntity):Boolean{
        try {
            dao.deleteExpense(expenseEntity)
            return true
        }
        catch (ex:Throwable){ 
            return false
        }
    }

    fun getBalance(list: List<ExpenseEntity>):String{
        var total =0.0
        list.forEach{
            if (it.type=="Income"){
                total+=it.amount
            }else{
                total-=it.amount
            }
        }
        return "₹ ${total}"
    }

    fun getTotalExpense(list: List<ExpenseEntity>):String{
        var total =0.0
        list.forEach{
            if (it.type=="Expense"){
                total+=it.amount
            }
        }
        return "₹ ${total}"
    }

    fun getTotalIncome(list: List<ExpenseEntity>):String{
        var total =0.0
        list.forEach{
            if (it.type=="Income"){
                total+=it.amount
            }
        }
        return "₹ ${total}"
    }

    fun getItemIcon(item:ExpenseEntity):Int{
        if (item.category=="Income"){
            return R.drawable.profits
        }
        else if (item.category=="Expense"){
            return R.drawable.loss
        }
        return R.drawable.profits
    }
}


class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dao = ExpenseDatabase.getDatabase(context).expenseDao()
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}