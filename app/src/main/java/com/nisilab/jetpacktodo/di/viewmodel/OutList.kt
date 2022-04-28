package com.nisilab.jetpacktodo.di.viewmodel

data class OutList(
    val list: List<OutItem>? = emptyList()
) {
    fun changeOpenFlg(itemId: Int): OutList{
        val next = this.list
        next?.find{ it.todo.id == itemId }!!.changeOpenFlg()
        return OutList(next)
    }
    fun changeFinishFlg(itemId: Int): OutList{
        val next = this.list
        next?.find{ it.todo.id == itemId }!!.changeFinishFlg()
        return OutList(next)
    }

    fun getOutList(): List<OutItem>?{
        return if(this.list.isNullOrEmpty()){
            null
        } else {
            this.list
        }
    }
}