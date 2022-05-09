package com.nisilab.jetpacktodo.di.viewmodel

import android.util.Log

data class OutList(
    val list: List<OutItem>? = emptyList()
) {
    fun changeOpenFlg(itemId: Int): OutList{
        Log.d("checkMove","changeOpen")
        val next = this.list
        next?.find{ it.todo.id == itemId }!!.changeOpenFlg()
        return OutList(next)
    }
    fun changeFinishFlg(itemId: Int): OutList{
        Log.d("checkMove","changeFinish")
        val next = this.list
        next?.find{ it.todo.id == itemId }!!.changeFinishFlg()
        return OutList(next)
    }
}