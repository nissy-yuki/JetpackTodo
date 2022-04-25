package com.nisilab.jetpacktodo.di.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nisilab.jetpacktodo.di.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val _todoItems = MutableStateFlow(TodoList(repository.loadItems()))
    private val _outAllItems = MutableStateFlow(_todoItems.value.toOutItems())
    private val _outItems = MutableStateFlow(_outAllItems.value)

    val todoItems: StateFlow<TodoList> = _todoItems
    val outAllItems: StateFlow<List<OutItem>> = _outAllItems
    val outItems: StateFlow<List<OutItem>> = _outItems


}