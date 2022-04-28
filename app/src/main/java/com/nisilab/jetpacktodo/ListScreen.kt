package com.nisilab.jetpacktodo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nisilab.jetpacktodo.di.viewmodel.ListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun listScreen(viewModel: ListViewModel = viewModel(),toEdit: () -> Unit){
    editButton { toEdit }
}

@Composable
fun editButton(action: () -> Unit){
    Box(contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(modifier = Modifier.padding(32.dp), onClick = { action() }) {
            Icon(Icons.Filled.Add, contentDescription = "add")
        }
    }
}