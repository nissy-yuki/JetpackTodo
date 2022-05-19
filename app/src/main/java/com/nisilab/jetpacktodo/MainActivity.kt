package com.nisilab.jetpacktodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
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
                    val navController = rememberNavController()
                    TodoNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun TodoNavHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = "list"){
        composable("list") {
            val viewModel: ListViewModel = hiltViewModel()
            ListScreen(viewModel) {
                navController.navigate("edit")
            }
        }
        composable("edit") {
            val viewModel: EditViewModel = hiltViewModel()
            EditScreen(viewModel) {
                navController.popBackStack()
            }
        }
    }

}