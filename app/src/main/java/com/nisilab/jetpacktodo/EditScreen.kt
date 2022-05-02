package com.nisilab.jetpacktodo

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.nisilab.jetpacktodo.di.viewmodel.EditViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EditScreen(
    viewModel: EditViewModel = viewModel(),
    navController: NavController,
    toList: () -> Unit
) {
    val title by viewModel.editTitle.collectAsState()
    val deadLine by viewModel.editDateTime.collectAsState()
    val tag by viewModel.editTag.collectAsState()
    val text by viewModel.editText.collectAsState()
    val focusManager = LocalFocusManager.current



    Surface(modifier = Modifier
        .fillMaxSize()
        .clickable(
            interactionSource = MutableInteractionSource(),
            indication = null,
        ) { focusManager.clearFocus() }) {

        val snackState = remember { SnackbarHostState() }
        val snackScope = rememberCoroutineScope()

        SnackbarHost(hostState = snackState, Modifier)
        fun launchSnackBar() {
            snackScope.launch { snackState.showSnackbar("タイトルを入力してください") }
        }
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            elmTextField(value = title, label = "title", changeValue = viewModel::setTitle)
            elmTextField(value = tag, label = "tag", changeValue = viewModel::setTag)
            elmTextField(value = text, label = "text", changeValue = viewModel::setText)
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceAround) {
                backButton {
                    toList()
                }
                saveButton(saveFlg = !title.isNullOrBlank(), toList = toList, saveAction = viewModel::saveTodo, showSnack = ::launchSnackBar)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun elmTextField(value: String, label: String?, changeValue: (String) -> Unit) {
    val requester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    OutlinedTextField(
        modifier = Modifier
            .padding(16.dp)
            .bringIntoViewRequester(requester)
            .onFocusEvent { focusState ->
                if (focusState.isFocused) {
                    coroutineScope.launch {
                        delay(200)
                        requester.bringIntoView()
                    }
                }
            }, value = value,
        onValueChange = {
            changeValue(it)
        },
        label = { Text(text = label ?: "") },
        singleLine = true
    )
}

@Composable
fun backButton(action: () -> Unit) {
    Button(onClick = { action() }) {
        Text(text = "back")
    }
}

@Composable
fun saveButton(saveFlg: Boolean,toList: () -> Unit,saveAction:() -> Unit,showSnack: () -> Unit) {
    Button(onClick = {
        if (saveFlg){
            saveAction()
            toList()
        } else {
            showSnack()
        }
    }) {
        Text(text = "save")
    }
}

@