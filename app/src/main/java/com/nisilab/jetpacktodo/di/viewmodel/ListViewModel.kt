package com.nisilab.jetpacktodo.di.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisilab.jetpacktodo.di.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor( private val repository: DataRepository) : ViewModel() {

    private val _todoItems: MutableStateFlow<TodoList> = MutableStateFlow(TodoList())
    private val _outAllItems: MutableStateFlow<OutList> = MutableStateFlow(OutList(_todoItems.value.toOutItems()))
    private val _outItems: MutableStateFlow<OutList> = MutableStateFlow(OutList(_outAllItems.value.list))

    val todoItems: StateFlow<TodoList?> = _todoItems
    val outAllItems: StateFlow<OutList?> = _outAllItems
    val outItems: StateFlow<OutList?> = _outItems

    fun setItems(){
        Log.d("checkMove","set item")
        viewModelScope.launch {
            val items = repository.loadItems()
            _todoItems.value = TodoList(items)
            _outAllItems.value = OutList(_todoItems.value.toOutItems())
            _outItems.value = _outAllItems.value
        }
    }

    fun updateTodo(itemId: Int){
        _todoItems.value = _todoItems.value.changeFinishFlg(itemId)
    }

    fun updateAllOut(itemId: Int){
        _outAllItems.value = _outAllItems.value.changeOpenFlg(itemId)
    }

    fun updateOut(itemId: Int){
        _outAllItems.value = _outAllItems.value.changeOpenFlg(itemId)
    }

}