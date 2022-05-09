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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nisilab.jetpacktodo.di.viewmodel.ListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nisilab.jetpacktodo.di.viewmodel.OutItem
import java.time.format.DateTimeFormatter

@Composable
fun listScreen(viewModel: ListViewModel = viewModel(),toEdit: () -> Unit){
    val items by viewModel.outItems.collectAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        items?.list?.also{
            ListCompose(it,viewModel::updateTodoFinishFlg,viewModel::updateAllOutOpenFlg)
        } ?: run {
            noDataText()
        }
        toEditButton { toEdit() }
    }
}

@Composable
fun ListCompose(outItems: List<OutItem>,checkButtonAction:(Int) -> Unit,arrowButtonAction:(Int) -> Unit) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){
        items(outItems){ toDo ->
            listItem(toDo = toDo, checkButtonAction = checkButtonAction, arrowButtonAction = arrowButtonAction)
        }
    }
}

@Composable
fun listItem(toDo: OutItem, checkButtonAction: (Int) -> Unit, arrowButtonAction: (Int) -> Unit) {
    Card(shape = RoundedCornerShape(20.dp)) {
        Column(modifier = Modifier.clickable(enabled = true,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false)
        ) {
            arrowButtonAction(toDo.todo.id)
        }) {
            itemHeadContents(item = toDo, checkButtonAction = checkButtonAction, arrowButtonAction = arrowButtonAction)
        }
    }
}

@Composable
fun itemHeadContents(item: OutItem, checkButtonAction: (Int) -> Unit, arrowButtonAction: (Int) -> Unit){
    Column() {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            checkButton(checkFlg = item.todo.isFinish, action = { checkButtonAction(item.todo.id) })
            headTextSet(title = item.todo.title, deadLine = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(item.todo.deadLine))
            arrowButton(isOpen = item.isOpen, action = { arrowButtonAction(item.todo.id) })
        }
        bodyItemSet(tag = item.todo.tag, text = item.todo.text)
        
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
        val iconImg = if (isOpen)Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
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
fun bodyItemSet(tag: String?, text: String?){
    Column {
        Text(text = tag ?: "なし")
        Text(text = text ?: "なし")
        underButtonSet()
    }
}

@Composable
fun underButtonSet(){
    Row(modifier = Modifier.padding(start = 16.dp, bottom = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        itemEditButton {
            Log.d("checkMove","push edit")
        }
        itemDeleteButton {
            Log.d("checkMove","push delete")
        }
    }
}

@Composable
fun itemEditButton(action: () -> Unit){
    Row(modifier = Modifier.clickable { action() }) {
        Icon(Icons.Filled.Edit, contentDescription = "editItem", tint = Color.Yellow)
        Text(text = "Edit", color = Color.Yellow)
    }
}

@Composable
fun itemDeleteButton(action: () -> Unit){
    Row(modifier = Modifier.clickable { action() }) {
        Icon(Icons.Filled.Delete, contentDescription = "deleteItem", tint = Color.Red)
        Text(text = "Delete", color = Color.Red)
    }
}

@Composable
fun toEditButton(action: () -> Unit){
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
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = Modifier,text = "no data")
    }
}