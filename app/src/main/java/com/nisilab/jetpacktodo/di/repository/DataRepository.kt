package com.nisilab.jetpacktodo.di.repository

import com.nisilab.jetpacktodo.di.database.TodoItem
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun loadItems(): Flow<List<TodoItem>>
    fun addItem(item: TodoItem)
    fun updateItem(item: TodoItem)
    fun deleteItem(item: TodoItem)
}