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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nisilab.jetpacktodo.di.viewmodel.EditViewModel
import com.nisilab.jetpacktodo.di.viewmodel.ListViewModel
import com.nisilab.jetpacktodo.ui.theme.JetpackTodoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            setContent {
                JetpackTodoTheme {
                    // A surface container using the 'background' color from the theme
                    val navController = rememberAnimatedNavController()
                    TodoNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun TodoNavHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            val viewModel = hiltViewModel<ListViewModel>()
            listScreen(viewModel = viewModel) {
                navController.navigate(
                    "edit"
                )
            }
        }
        composable("edit") {
            val viewModel = hiltViewModel<EditViewModel>()
            EditScreen(viewModel = viewModel) {
                navController.popBackStack()
            }
        }
    }
}
