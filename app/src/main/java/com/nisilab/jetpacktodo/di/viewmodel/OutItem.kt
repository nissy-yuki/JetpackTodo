package com.nisilab.jetpacktodo.di.viewmodel

import com.nisilab.jetpacktodo.di.database.TodoItem
import java.time.LocalDateTime

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
