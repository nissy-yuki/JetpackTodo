package com.nisilab.jetpacktodo.di.Model

import com.nisilab.jetpacktodo.di.database.TodoItem

data class TodoList(
    val list: List<TodoItem> = emptyList()
){
    fun toOutItems(): List<OutItem>{
        return if(this.list.isNullOrEmpty()){
            emptyList()
        } else {
            var next = mutableListOf<OutItem>()
            this.list.forEach {
                next.add(it.toOutItem())
            }
            next
        }
    }

    fun changeFinishFlg(itemId: Int){
        this.list.find{it.id == itemId}!!.changeFinishFlg()
    }
}
