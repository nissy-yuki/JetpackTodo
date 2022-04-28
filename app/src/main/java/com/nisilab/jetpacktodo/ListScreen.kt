package com.nisilab.jetpacktodo

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nisilab.jetpacktodo.di.viewmodel.ListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nisilab.jetpacktodo.di.database.TodoItem
import com.nisilab.jetpacktodo.di.viewmodel.OutItem
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@Composable
fun listScreen(viewModel: ListViewModel = viewModel(),toEdit: () -> Unit){
    viewModel.setItems()
    val items by viewModel.outItems.collectAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        items?.getOutList()?.also{
            ListCompose(it)
        } ?: run {
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

@Preview
@Composable
fun listItemTest() = listItem(toDo = TodoItem(title = "hoge", deadLine = ZonedDateTime.now().truncatedTo(
    ChronoUnit.MINUTES).toLocalDateTime(), tag = "name", text = "huha").toOutItem())

@Composable
fun listItem(toDo: OutItem) {
    Card(shape = RoundedCornerShape(20.dp)) {
        Column(modifier = Modifier.clickable(enabled = true,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false)
        ) {
            toDo.changeOpenFlg()
        }) {
            itemHeadContents(item = toDo)
        }
    }
}

@Composable
fun itemHeadContents(item: OutItem){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        checkButton(checkFlg = item.todo.isFinish, action = { item.changeFinishFlg() })
        headTextSet(title = item.todo.title, deadLine = item.todo.deadLine.toString())
        arrowButton(isOpen = item.isOpen, action = { item.changeOpenFlg() })
    }
}

@Composable
fun checkButton(checkFlg: Boolean, action: () -> Unit){
    Icon(
        Icons.Filled.Check,
        contentDescription = "check",
        modifier = Modifier
            .size(50.dp)
            .padding(end = 16.dp)
            .clickable(
                enabled = true,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false)
            ) {
                action()
            },
        if (checkFlg)  Color.Green else Color.Gray
    )
}

@Composable
fun headTextSet(title: String, deadLine: String){
    Column(modifier = Modifier.padding(top = 5.dp)) {
        Text(text = deadLine, fontSize = 10.sp)
        Text(text = title, fontSize = 15.sp)
    }
}

@Composable
fun arrowButton(isOpen: Boolean, action: () -> Unit){
    Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.BottomEnd) {
        val iconImg = if (isOpen)Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropDown
        Icon(iconImg, contentDescription = "Open", modifier = Modifier.clickable(
            enabled = true,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false)
        ) {
            action()
        })
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