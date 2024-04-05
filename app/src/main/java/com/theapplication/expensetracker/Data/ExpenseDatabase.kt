package com.theapplication.expensetracker.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.theapplication.expensetracker.Data.Dao.ExpenseDao
import com.theapplication.expensetracker.Data.Model.ExpenseEntity

@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDatabase:RoomDatabase() {
    abstract fun expenseDao():ExpenseDao

    companion object{
        const val DATABASE_NAME="expense_database"
        @JvmStatic
        fun getDatabase(context: Context):ExpenseDatabase{
            return Room.databaseBuilder(
                context,ExpenseDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}