package com.nisilab.jetpacktodo.di.database

import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_table")
    suspend fun loadAllItem(): List<TodoItem>

    @Insert
    suspend fun addItem(item: TodoItem)

    @Update
    suspend fun updateItem(item: TodoItem)

    @Delete
    suspend fun deleteItem(item: TodoItem)
}