package com.nisilab.jetpacktodo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.nisilab.jetpacktodo.di.viewmodel.EditViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Composable
fun EditScreen(
    viewModel: EditViewModel = viewModel(),
    toList: () -> Unit
) {

    val title by viewModel.editTitle.collectAsState()
    val deadDate by viewModel.editDate.collectAsState()
    val deadTime by viewModel.editTime.collectAsState()
    val tag by viewModel.editTag.collectAsState()
    val text by viewModel.editText.collectAsState()

    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    SnackbarHost(hostState = snackState, Modifier)

    val focusManager = LocalFocusManager.current

    Surface(modifier = Modifier
        .fillMaxSize()
        .clickable(
            interactionSource = MutableInteractionSource(),
            indication = null,
        ) { focusManager.clearFocus() }) {

        fun launchSnackBar() {
            snackScope.launch { snackState.showSnackbar("タイトルを入力してください") }
        }

        fun saveButtonAction(flg: Boolean) {
            if (flg) {
                viewModel.saveTodo()
                toList()
            } else {
                launchSnackBar()
            }
        }

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            elmTextField(value = title, label = "title", changeValue = viewModel::setTitle)
            deadLineField(
                date = deadDate,
                time = deadTime,
                changeDate = viewModel::setDate,
                changeTime = viewModel::setTime
            )
            elmTextField(value = tag, label = "tag", changeValue = viewModel::setTag)
            elmTextField(value = text, label = "text", changeValue = viewModel::setText)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                backButton(action = toList)
                saveButton(saveFlg = !title.isNullOrBlank(), action = ::saveButtonAction)
            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun elmTextField(value: String, label: String?, changeValue: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
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
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        singleLine = true
    )
}

@Composable
fun deadLineField(
    date: LocalDate,
    time: LocalTime,
    changeDate: (LocalDate) -> Unit,
    changeTime: (LocalTime) -> Unit
) {
    Row() {
        elmDateField(value = date, changeValue = changeDate)
        elmTimeField(value = time, changeValue = changeTime)
    }
}

@Composable
fun elmDateField(value: LocalDate, changeValue: (LocalDate) -> Unit) {
    val context = LocalContext.current

    Text(modifier = Modifier.clickable {
        showDatePicker(context = context, onDecideDate = changeValue, value)
    }, text = value.toString())
}

fun showDatePicker(
    context: Context,
    onDecideDate: (LocalDate) -> Unit,
    date: LocalDate
) {
    val year = date.year
    val month = date.monthValue
    val day = date.dayOfMonth

    DatePickerDialog(
        context,
        { _: DatePicker, pickedYear: Int, pickedMonth: Int, pickedDay: Int ->
            onDecideDate(LocalDate.of(pickedYear, pickedMonth, pickedDay))
        }, year, month, day
    ).show()
}


@Composable
fun elmTimeField(value: LocalTime, changeValue: (LocalTime) -> Unit) {
    Text(text = value.toString())
}

//fun showTimePicker(
//    context: Context,
//    onDecideDate: (LocalTime) -> Unit,
//    time: LocalTime
//) {
//    val hour = time.hour
//    val minute = time.minute
//
//    TimePickerDialog(
//        context,
//        { _: TimePicker, pickedHour: Int, pickedMinute: Int->
//            onDecideDate(LocalTime.of(pickedHour, pickedMinute))
//        }, hour, minute
//    ).show()
//
//}

@Composable
fun backButton(action: () -> Unit) {
    Button(onClick = { action() }) {
        Text(text = "back")
    }
}

@Composable
fun saveButton(saveFlg: Boolean, action: (Boolean) -> Unit) {
    Button(onClick = {
        action(saveFlg)
    }) {
        Text(text = "save")
    }
}