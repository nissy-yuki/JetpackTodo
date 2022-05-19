package com.nisilab.jetpacktodo.di.Model

import com.nisilab.jetpacktodo.di.database.TodoItem

data class OutItem(
    val todo: TodoItem,
    var isOpen: Boolean = false
){
    fun changeOpenFlg(){
        this.isOpen = !this.isOpen
    }

    fun changeFinishFlg(){
        this.todo.isFinish = !this.todo.isFinish
    }
}
