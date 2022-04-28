package com.nisilab.jetpacktodo

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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

@Composable
fun listScreen(viewModel: ListViewModel = viewModel(),toEdit: () -> Unit){
    viewModel.setItems()
    val items by viewModel.outItems.collectAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        items?.getOutList()?.also{
            Log.d("checkMove","have data")
            ListCompose(it)
        } ?: run {
            Log.d("checkMove","no data")
            noDataText()
        }
        editButton { toEdit() }
    }
}

@Composable
fun ListCompose(outItems: List<OutItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(outItems){ toDo ->
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

@Composable
fun noDataText(){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Text(modifier = Modifier,text = "no data")
    }
}