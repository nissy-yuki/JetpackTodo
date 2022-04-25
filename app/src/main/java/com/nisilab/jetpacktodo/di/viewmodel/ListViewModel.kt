package com.nisilab.jetpacktodo.di.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nisilab.jetpacktodo.di.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val todoItems: MutableState<TodoList> = mutableStateOf(TodoList(repository.loadItems()))
}