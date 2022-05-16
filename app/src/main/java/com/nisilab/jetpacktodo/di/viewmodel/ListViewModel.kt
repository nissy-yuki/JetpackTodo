package com.nisilab.jetpacktodo.di.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisilab.jetpacktodo.di.database.TodoItem
import com.nisilab.jetpacktodo.di.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor( private val repository: DataRepository) : ViewModel() {

    private var _todoItems: TodoList = TodoList()
    private var _outAllItems: OutList = OutList(_todoItems.toOutItems())
    private val _outItems: MutableStateFlow<List<OutItem>> = MutableStateFlow(_outAllItems.list ?: emptyList())

    val outItems: StateFlow<List<OutItem>> = _outItems.asStateFlow()

    init{
        viewModelScope.launch {
            _todoItems = TodoList(repository.loadItems())
            _outAllItems = OutList(_todoItems.toOutItems())
            setOutItems()
        }
    }

    private fun setOutItems(){
        _outItems.value = _outAllItems.list
    }

    fun updateTodoFinishFlg(itemId: Int){
        _todoItems.changeFinishFlg(itemId)
        Log.d("checkValue","$_todoItems")
        updateAllOutFinishFlg(itemId)
    }

    private fun updateAllOutFinishFlg(itemId: Int){
        _outAllItems.changeFinishFlg(itemId)
        setOutItems()
    }

    fun updateAllOutOpenFlg(itemId: Int){
        _outAllItems.changeOpenFlg(itemId)
        setOutItems()
    }

    fun updateDbItem(item: TodoItem){
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }
}