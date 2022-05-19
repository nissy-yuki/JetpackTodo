package com.nisilab.jetpacktodo.di.repository

import com.nisilab.jetpacktodo.di.database.TodoDao
import com.nisilab.jetpacktodo.di.database.TodoItem
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val Dao: TodoDao) : DataRepository {
    override fun loadItems() = Dao.loadAllItem()

    override fun addItem(item: TodoItem) = Dao.addItem(item)

    override fun updateItem(item: TodoItem) = Dao.updateItem(item)

    override fun deleteItem(item: TodoItem) = Dao.deleteItem(item)

    fun loadItemsSortByDeadLine() = loadItems().map {
        it -> it.sortedBy { it.deadLine }
    }

}