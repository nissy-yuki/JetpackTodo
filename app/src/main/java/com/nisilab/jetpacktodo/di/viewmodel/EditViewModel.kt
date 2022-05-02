package com.nisilab.jetpacktodo.di.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisilab.jetpacktodo.di.database.TodoItem
import com.nisilab.jetpacktodo.di.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val _editTitle: MutableStateFlow<String> = MutableStateFlow("")
    private val _editDate: MutableStateFlow<LocalDate> = MutableStateFlow((ZonedDateTime.now()).truncatedTo(ChronoUnit.DAYS).toLocalDate())
    private val _editTime: MutableStateFlow<LocalTime> = MutableStateFlow((ZonedDateTime.now()).truncatedTo(ChronoUnit.MINUTES).toLocalTime())
    private val _editTag: MutableStateFlow<String> = MutableStateFlow("")
    private val _editText: MutableStateFlow<String> = MutableStateFlow("")

    val editTitle: StateFlow<String> = _editTitle
    val editDate: StateFlow<LocalDate> = _editDate
    val editTime: StateFlow<LocalTime> = _editTime
    val editTag: StateFlow<String> = _editTag
    val editText: StateFlow<String> = _editText

    fun setTitle(value: String){
        _editTitle.value = value
    }

    fun setDate(value: LocalDate){
        _editDate.value = value
    }

    fun setTime(value: LocalTime){
        _editTime.value = value
    }

    fun setTag(value: String){
        _editTag.value = value
    }

    fun setText(value: String){
        _editText.value = value
    }

    fun saveTodo(){
        viewModelScope.launch {
            repository.addItem(
                TodoItem(
                    title = _editTitle.value,
                    deadLine = LocalDateTime.of(_editDate.value,_editTime.value),
                    tag = _editTag.value,
                    text = _editText.value
                )
            )
        }
    }
}