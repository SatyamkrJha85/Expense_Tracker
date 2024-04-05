package com.theapplication.expensetracker.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.theapplication.expensetracker.Data.Model.ExpenseEntity
import java.util.concurrent.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM Expense_table")
    fun gelAllExpense():kotlinx.coroutines.flow.Flow<List<ExpenseEntity>>

    @Insert
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)

    @Update
    suspend fun updateExpense(expenseEntity: ExpenseEntity)
}