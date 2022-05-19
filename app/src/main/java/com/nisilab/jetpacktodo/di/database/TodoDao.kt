package com.nisilab.jetpacktodo.di.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_table")
    fun loadAllItem(): Flow<List<TodoItem>>

    @Insert
    fun addItem(item: TodoItem)

    @Update
    fun updateItem(item: TodoItem)

    @Delete
    fun deleteItem(item: TodoItem)
}