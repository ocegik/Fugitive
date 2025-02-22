package com.example.fugitive

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyApp(){
    val navController = rememberNavController()
    AppNavGraph(navController)

}