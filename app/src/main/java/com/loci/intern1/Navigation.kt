package com.loci.intern1

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { Login(navController = navController) }
        composable("signup") { SignUpScreen(navController = navController) }
        composable("home") { HomeScreen(navController = navController) }
    }
}