package com.nisilab.jetpacktodo.di.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nisilab.jetpacktodo.di.database.TodoItem
import com.nisilab.jetpacktodo.di.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val _todoItems = MutableStateFlow(TodoList(repository.loadItems()))
    private val _outAllItems = MutableStateFlow(OutList(_todoItems.value.toOutItems()))
    private val _outItems = MutableStateFlow(OutList(_outAllItems.value.list))

    val todoItems: StateFlow<TodoList> = _todoItems
    val outAllItems: StateFlow<OutList> = _outAllItems
    val outItems: StateFlow<OutList> = _outItems

    fun updateTodo(item: TodoItem){
        _todoItems.value = _todoItems.value.changeFinishFlg(item)
    }

    fun updateAllOut(item: OutItem){
        _outAllItems.value = _outAllItems.value.changeOpenFlg(item)
    }

    fun updateOut(item: OutItem){
        _outAllItems.value = _outAllItems.value.changeOpenFlg(item)
    }

}