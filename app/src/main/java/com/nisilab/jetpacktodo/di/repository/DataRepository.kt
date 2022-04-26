package com.nisilab.jetpacktodo.di.repository

import com.nisilab.jetpacktodo.di.database.TodoItem

interface DataRepository {
    suspend fun loadItems(): List<TodoItem>
    suspend fun addItem(item: TodoItem)
    suspend fun updateItem(item: TodoItem)
    suspend fun deleteItem(item: TodoItem)
}