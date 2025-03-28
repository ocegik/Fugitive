package com.example.fugitive.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.fugitive.navigation.AppNavGraph

@Composable
fun MyApp(){
    val navController = rememberNavController()
    AppNavGraph(navController)

}