package com.nisilab.jetpacktodo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nisilab.jetpacktodo.di.viewmodel.ListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nisilab.jetpacktodo.di.viewmodel.OutItem
import com.nisilab.jetpacktodo.di.viewmodel.OutList

@Composable
fun listScreen(viewModel: ListViewModel = viewModel(),toEdit: () -> Unit){
    viewModel.setItems()
    val items by viewModel.outItems.collectAsState()

    items?.let { ListCompose(it) }
    editButton { toEdit }
}

@Composable
fun ListCompose(outItems: OutList) {
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(outItems.list){ toDo ->
            listItem(toDo = toDo)
        }
    }
}

@Composable
fun listItem(toDo: OutItem) {
    Card() {

    }
}



@Composable
fun editButton(action: () -> Unit){
    Box(contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(modifier = Modifier.padding(32.dp), onClick = { action() }) {
            Icon(Icons.Filled.Add, contentDescription = "add")
        }
    }
}