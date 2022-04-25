package com.nisilab.jetpacktodo.di.viewmodel

data class OutList(
    val list: List<OutItem> = emptyList()
) {
    fun changeOpenFlg(item: OutItem): OutList{
        val next = this.list
        next.find{ it.id == item.id }!!.changeOpenFlg()
        return OutList(next)
    }
    fun changeFinishFlg(item: OutItem): OutList{
        val next = this.list
        next.find{ it.id == item.id }!!.changeFinishFlg()
        return OutList(next)
    }
}