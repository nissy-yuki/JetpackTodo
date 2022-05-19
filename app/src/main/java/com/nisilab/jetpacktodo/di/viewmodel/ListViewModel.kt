package com.nisilab.jetpacktodo.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisilab.jetpacktodo.di.Model.OutItem
import com.nisilab.jetpacktodo.di.Model.TodoList
import com.nisilab.jetpacktodo.di.database.TodoItem
import com.nisilab.jetpacktodo.di.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor( private val repository: DataRepository) : ViewModel() {

    private val _outItems: MutableStateFlow<List<OutItem>> = MutableStateFlow(emptyList())

    val outItems: StateFlow<List<OutItem>> = _outItems.asStateFlow()

    fun initFunc(){
        viewModelScope.launch {
            setOutItems(TodoList(repository.loadItems()).toOutItems())
        }
    }

    private fun setOutItems(list: List<OutItem>){
        _outItems.value = list
    }

    fun updateTodoFinishFlg(item:OutItem) {
        val next = _outItems.value.toMutableList()
        next[next.indexOf(item)].todo.isFinish = !item.todo.isFinish
        setOutItems(next.toList())
        updateDbItem(item.todo)
    }

    fun updateOutItemOpenFlg(item: OutItem){
        val next = _outItems.value.toMutableList()
        next[next.indexOf(item)].isOpen = !item.isOpen
        setOutItems(next.toList())
    }

    fun deleteItem(item: OutItem){
        val next = _outItems.value.toMutableList()
        next.remove(item)
        setOutItems(next.toList())
        deleteDbItem(item.todo)
    }

    private fun updateDbItem(item: TodoItem){
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

    private fun deleteDbItem(item: TodoItem){
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }
}