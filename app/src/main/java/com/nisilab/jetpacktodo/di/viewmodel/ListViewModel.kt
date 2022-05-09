package com.nisilab.jetpacktodo.di.viewmodel

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

    init {
        viewModelScope.launch {
            _todoItems.value = TodoList(repository.loadItems())
            _outAllItems.value = OutList(_todoItems.value.toOutItems())
            setOutItems()
        }
    }

    private fun setOutItems(){
        _outItems.value = _outAllItems.value
    }

    fun updateTodoFinishFlg(itemId: Int){
        _todoItems.value = _todoItems.value.changeFinishFlg(itemId)
        updateAllOutFinishFlg(itemId)
    }

    private fun updateAllOutFinishFlg(itemId: Int){
        _outAllItems.value = _outAllItems.value.changeFinishFlg(itemId)
        setOutItems()
    }

    fun updateAllOutOpenFlg(itemId: Int){
        _outAllItems.value = _outAllItems.value.changeOpenFlg(itemId)
        setOutItems()
    }

}