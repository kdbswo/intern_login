package com.loci.intern1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.loci.intern1.ui.theme.Intern1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Intern1Theme {
                Navigation()
            }
        }
    }
}

@Composable
fun Login(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController
) {

    val loginState = viewModel.auth?.currentUser != null

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        

        Text(
            text = "Hello $name! dddddd",
            modifier = modifier
        )
        Text(
            text = "Hello $name! dddddd",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    Intern1Theme {
        Navigation()
    }
}