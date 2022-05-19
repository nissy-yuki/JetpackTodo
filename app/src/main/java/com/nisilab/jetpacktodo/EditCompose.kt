package com.nisilab.jetpacktodo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
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
import java.time.LocalTime

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
            ElmTextField(value = title, label = "title", changeValue = viewModel::setTitle)
            DeadLineField(
                date = deadDate,
                time = deadTime,
                changeDate = viewModel::setDate,
                changeTime = viewModel::setTime
            )
            ElmTextField(value = tag, label = "tag", changeValue = viewModel::setTag)
            ElmTextField(value = text, label = "text", changeValue = viewModel::setText)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BackButton(action = toList)
                SaveButton(saveFlg = title.isNotBlank(), action = ::saveButtonAction)
            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun ElmTextField(value: String, label: String?, changeValue: (String) -> Unit) {
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
fun DeadLineField(
    date: LocalDate,
    time: LocalTime,
    changeDate: (LocalDate) -> Unit,
    changeTime: (LocalTime) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        ElmDateField(value = date, changeValue = changeDate)
        ElmTimeField(value = time, changeValue = changeTime)
    }
}

@Composable
fun ElmDateField(value: LocalDate, changeValue: (LocalDate) -> Unit) {
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
fun ElmTimeField(value: LocalTime, changeValue: (LocalTime) -> Unit) {
    val context = LocalContext.current
    Text(modifier = Modifier.clickable {
        showTimePicker(context = context, onDecideDate = changeValue, value)
    }, text = value.toString())
}

fun showTimePicker(
    context: Context,
    onDecideDate: (LocalTime) -> Unit,
    time: LocalTime
) {
    val hour = time.hour
    val minute = time.minute

    TimePickerDialog(
        context,
        { _, PickerHour: Int, PickerMinute: Int ->
            onDecideDate(LocalTime.of(PickerHour, PickerMinute))
        }, hour, minute, false
    ).show()


}

@Composable
fun BackButton(action: () -> Unit) {
    Button(onClick = { action() }) {
        Text(text = "back")
    }
}

@Composable
fun SaveButton(saveFlg: Boolean, action: (Boolean) -> Unit) {
    Button(onClick = {
        action(saveFlg)
    }) {
        Text(text = "save")
    }
}