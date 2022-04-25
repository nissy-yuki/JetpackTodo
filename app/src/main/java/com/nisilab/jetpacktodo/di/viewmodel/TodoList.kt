package com.nisilab.jetpacktodo.di.viewmodel

import com.nisilab.jetpacktodo.di.database.TodoItem

data class TodoList(
    val list: List<TodoItem>? = emptyList()
){
    fun toOutItems(): List<OutItem>{
        var next = mutableListOf<OutItem>()
        this.list!!.forEach {
            next.add(it.toOutItem())
        }
        return next
    }

    fun changeFinishFlg(item: TodoItem): TodoList{
        var next = this.list
        next!!.find{it.id == item.id}!!.changeFinishFlg()
        return TodoList(next)
    }
}
