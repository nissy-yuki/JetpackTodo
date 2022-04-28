package com.nisilab.jetpacktodo.di.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nisilab.jetpacktodo.di.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val _editTitle: MutableStateFlow<String> = MutableStateFlow("")
    private val _editDateTime: MutableStateFlow<ZonedDateTime> = MutableStateFlow(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS))
    private val _editTag: MutableStateFlow<String> = MutableStateFlow("")
    private val _editText: MutableStateFlow<String> = MutableStateFlow("")

    val editTitle: StateFlow<String> = _editTitle
    val editDateTime: StateFlow<ZonedDateTime> = _editDateTime
    val editTag: StateFlow<String> = _editTag
    val editText: StateFlow<String> = _editText

    fun setTitle(value: String){
        _editTitle.value = value
    }

    fun setDateTime(value: ZonedDateTime){
        _editDateTime.value = value
    }

    fun setTag(value: String){
        _editTag.value = value
    }

    fun setText(value: String){
        _editText.value = value
    }
}