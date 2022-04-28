package com.nisilab.jetpacktodo

import androidx.compose.runtime.Composable
import com.nisilab.jetpacktodo.di.viewmodel.EditViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun EditScreen(viewModel: EditViewModel = viewModel(),navController:NavController ,toList:() -> Unit){

}

@Composable
fun backButton(action: () -> Unit){

}