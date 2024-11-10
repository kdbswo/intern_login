package com.loci.intern1

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.loci.intern1.ui.theme.Intern1Theme


@Composable
fun SignUpScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val signUpSuccess by viewModel.signUpSuccess.collectAsStateWithLifecycle()
    val signUpError by viewModel.signUpError.collectAsStateWithLifecycle()
    val isValidEmail by viewModel.isValidEmail.collectAsStateWithLifecycle()
    val isValidPassword by viewModel.isValidPassword.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(signUpError) {
        signUpError?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.deleteSignUpError()
        }
    }

    LaunchedEffect(signUpSuccess) {
        if (signUpSuccess) {
            navController.navigate("login") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp, 0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "회원가입",
            modifier = Modifier.align(Alignment.Start),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = email,
            onValueChange = {
                email = it
                viewModel.isValidEmail(email)
            },
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "") }
        )

        if (!isValidEmail) {
            Text(
                text = "유효한 이메일 형식이 아닙니다.",
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.Start),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.isValidPassword(password)
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "") },
            visualTransformation = PasswordVisualTransformation()
        )

        if (!isValidPassword) {
            Text(
                text = "비밀번호는 최소 6자 이상이어야 합니다",
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.Start),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = {
                viewModel.signUp(email, password)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF359739)),
            modifier = Modifier.fillMaxWidth(),
            enabled = isValidEmail && isValidPassword
        ) {
            Text(text = "회원가입 하기", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "로그인으로 돌아가기", modifier = Modifier
                .clickable {
                    navController.navigate("login") {
                        popUpTo("signUp") { inclusive = true }
                    }
                },
            color = Color.Gray,
            fontSize = 16.sp
        )

    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreView() {
    Intern1Theme {
        val navController = rememberNavController()

        SignUpScreen(
            navController = navController
        )
    }
}