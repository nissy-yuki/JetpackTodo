package com.nisilab.jetpacktodo.di.Model

data class OutList(
    val list: List<OutItem> = emptyList()
) {
    fun changeOpenFlg(itemId: Int){
        this.list.find{ it.todo.id == itemId }!!.changeOpenFlg()
    }
    fun changeFinishFlg(itemId: Int){
        this.list.find{ it.todo.id == itemId }!!.changeFinishFlg()
    }
}