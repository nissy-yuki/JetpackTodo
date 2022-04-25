package com.nisilab.jetpacktodo.di.repository

import com.nisilab.jetpacktodo.di.database.TodoDao
import com.nisilab.jetpacktodo.di.database.TodoItem
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val Dao: TodoDao) : DataRepository {
    override fun loadItems() = Dao.loadAllItem()

    override suspend fun addItem(item: TodoItem) = Dao.addItem(item)

    override suspend fun updateItem(item: TodoItem) = Dao.updateItem(item)

    override suspend fun deleteItem(item: TodoItem) = Dao.deleteItem(item)
}