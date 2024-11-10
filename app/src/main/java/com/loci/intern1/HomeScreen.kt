package com.loci.intern1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.loci.intern1.ui.theme.Intern1Theme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController
) {

    val isLogin by viewModel.isLogin.collectAsStateWithLifecycle()

    LaunchedEffect(isLogin) {
        if (!isLogin) {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Hello", fontSize = 30.sp)

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { viewModel.logout() }
        ) {
            Text(text = "로그아웃")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Intern1Theme {
        val navController = rememberNavController()

        HomeScreen(navController = navController)
    }
}