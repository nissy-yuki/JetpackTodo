package com.nisilab.jetpacktodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nisilab.jetpacktodo.di.viewmodel.EditViewModel
import com.nisilab.jetpacktodo.di.viewmodel.ListViewModel
import com.nisilab.jetpacktodo.ui.theme.JetpackTodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val listViewModel: ListViewModel by viewModels()
    private val editViewModel: EditViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackTodoTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberAnimatedNavController()
                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        listScreen(viewModel = listViewModel) {
                            navController.navigate(
                                "edit"
                            )
                        }
                    }
                    composable("edit") {
                        EditScreen(viewModel = editViewModel) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackTodoTheme {
        Greeting("Android")
    }
}