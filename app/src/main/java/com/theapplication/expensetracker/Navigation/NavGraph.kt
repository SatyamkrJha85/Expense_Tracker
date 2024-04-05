package com.theapplication.expensetracker.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.theapplication.expensetracker.Screens.AddExpense
import com.theapplication.expensetracker.Screens.HomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.HomeScreen.route){
        composable(Route.HomeScreen.route){
            HomeScreen(navController = navController)
        }

        composable(Route.AddexpenceScreen.route){
            AddExpense(navController=navController)
        }
    }

}

sealed class Route(val route:String){
    object HomeScreen:Route("homescreen")
    object AddexpenceScreen:Route("addexpense")
}