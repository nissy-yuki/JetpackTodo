package com.nisilab.jetpacktodo.di.repository

import com.nisilab.jetpacktodo.di.database.TodoDao
import com.nisilab.jetpacktodo.di.database.TodoItem
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val Dao: TodoDao) : DataRepository {
    override suspend fun loadItems(): List<TodoItem> {
        return Dao.loadAllItem()
    }

    override suspend fun addItem(item: TodoItem) {
        return Dao.addItem(item)
    }

    override suspend fun updateItem(item: TodoItem) {
        return Dao.updateItem(item)
    }

    override suspend fun deleteItem(item: TodoItem) {
        return Dao.deleteItem(item)
    }
}