package com.nisilab.jetpacktodo.di.viewmodel

import androidx.lifecycle.ViewModel
import com.nisilab.jetpacktodo.di.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

}