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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nisilab.jetpacktodo.di.viewmodel.ListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nisilab.jetpacktodo.di.database.TodoItem
import com.nisilab.jetpacktodo.di.Model.OutItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ListScreen(viewModel: ListViewModel = viewModel(), toEdit: () -> Unit){
    //viewModel.setItems()
    val items by viewModel.outItems.collectAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            SearchBox()
            items?.also{
                ListCompose(it,viewModel::updateTodoFinishFlg,viewModel::updateAllOutOpenFlg)
            } ?: run {
                NoDataText()
            }
        }

        ToEditButton { toEdit() }
    }
}

@Composable
fun SearchBox(){

}


@Composable
fun ListCompose(outItems: List<OutItem>, checkButtonAction:(Int) -> Unit, arrowButtonAction:(Int) -> Unit) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){
        items(outItems){ toDo ->
            ListItem(toDo = toDo, checkButtonAction = checkButtonAction, arrowButtonAction = arrowButtonAction)
        }
    }
}

@Preview
@Composable
fun TestListItem() = ListItem(toDo = OutItem(todo = TodoItem(id = 0, title = "hogehogehogehgeoh", deadLine = LocalDateTime.now(), tag = null, text = "hahaha", isFinish = false), isOpen = true), checkButtonAction = { num -> Log.d("checkMove","push check")}, arrowButtonAction = { num -> Log.d("checkMove","push check")} )

@Composable
fun ListItem(toDo: OutItem, checkButtonAction: (Int) -> Unit, arrowButtonAction: (Int) -> Unit) {
    Card(shape = RoundedCornerShape(20.dp)) {
        Column(modifier = Modifier.clickable(enabled = true,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false)
        ){
            arrowButtonAction(toDo.todo.id)
        }) {
            ItemHeadContents(item = toDo, checkButtonAction = checkButtonAction, arrowButtonAction = arrowButtonAction)
        }
    }
}

@Composable
fun ItemHeadContents(item: OutItem, checkButtonAction: (Int) -> Unit, arrowButtonAction: (Int) -> Unit){
    Column() {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            CheckButton(checkFlg = item.todo.isFinish, action = { checkButtonAction(item.todo.id) })
            HeadTextSet(title = item.todo.title, deadLine = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(item.todo.deadLine))
            ArrowButton(isOpen = item.isOpen, action = { arrowButtonAction(item.todo.id) })
        }
        if(item.isOpen){
            BodyItemSet(tag = item.todo.tag, text = item.todo.text)
        }
    }
    
}

@Composable
fun CheckButton(checkFlg: Boolean, action: () -> Unit){
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
        if(checkFlg) Color.Green else Color.Gray
    )
}

@Composable
fun HeadTextSet(title: String, deadLine: String){
    Column(modifier = Modifier
        .fillMaxWidth(0.9f)
        .padding(top = 5.dp)) {
        Text(text = deadLine, fontSize = 10.sp)
        Text(text = title, fontSize = 15.sp)
    }
}

@Composable
fun ArrowButton(isOpen: Boolean, action: () -> Unit){

        val iconImg = if (isOpen)Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
        Icon(iconImg, contentDescription = "Open", modifier = Modifier.clickable(
            enabled = true,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false)
        ) {
            action()
        })

}

@Composable
fun BodyItemSet(tag: String?, text: String?){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 50.dp, bottom = 8.dp, end = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = tag ?: "なし")
        Text(text = text ?: "なし")
        UnderButtonSet()
    }
}

@Composable
fun UnderButtonSet(){
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ItemEditButton {
            Log.d("checkMove","push edit")
        }
        ItemDeleteButton {
            Log.d("checkMove","push delete")
        }
    }
}

@Composable
fun ItemEditButton(action: () -> Unit){
    Row(modifier = Modifier.clickable { action() }, verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Filled.Edit, contentDescription = "editItem", tint = Color.Yellow)
        Text(text = "Edit", color = Color.Yellow)
    }
}

@Composable
fun ItemDeleteButton(action: () -> Unit){
    Row(modifier = Modifier.clickable { action() }, verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Filled.Delete, contentDescription = "deleteItem", tint = Color.Red)
        Text(text = "Delete", color = Color.Red)
    }
}

@Composable
fun ToEditButton(action: () -> Unit){
    Box(contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(modifier = Modifier.padding(32.dp), onClick = { action() }) {
            Icon(Icons.Filled.Add, contentDescription = "add")
        }
    }
}

@Composable
fun NoDataText(){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = Modifier,text = "no data")
    }
}