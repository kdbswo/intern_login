package com.loci.intern1

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.loci.intern1.ui.theme.Intern1Theme

@Composable
fun HomeScreen(navController: NavHostController) {
    Text(text = "Home")
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Intern1Theme {
        val navController = rememberNavController()
        
        HomeScreen(navController)
    }
}