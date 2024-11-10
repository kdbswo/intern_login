package com.loci.intern1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.loci.intern1.ui.theme.Intern1Theme

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Home")
        Button(onClick = { /*TODO*/ }) {
            Text(text = "로그아웃")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Intern1Theme {
        val navController = rememberNavController()

        HomeScreen(navController)
    }
}