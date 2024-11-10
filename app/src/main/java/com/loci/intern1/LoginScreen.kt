package com.loci.intern1

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun Login(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController
) {

    val loginState = viewModel.auth?.currentUser != null

    val loginSuccess by viewModel.loginSuccess.collectAsStateWithLifecycle()
    val isValidEmail by viewModel.isValidEmail.collectAsStateWithLifecycle()
    val isValidPassword by viewModel.isValidPassword.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isVisiblePassword by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loginError.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { message ->
                message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
    }

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    LaunchedEffect(loginState) {
        if (loginState) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
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
            text = "로그인", textAlign = TextAlign.Start,
            modifier = Modifier.align(Alignment.Start),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                viewModel.isValidEmail(email)
            },
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "") },
            textStyle = TextStyle(
                color = Color(0xFF359739),
                fontSize = 20.sp,
            ),
            trailingIcon = {
                IconButton(onClick = { email = "" }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
            },
            shape = CutCornerShape(12.dp)
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

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.isValidPassword(password)
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "") },
            visualTransformation = if (isVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (isVisiblePassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                IconButton(onClick = { isVisiblePassword = !isVisiblePassword }) {
                    Icon(
                        imageVector = image,
                        contentDescription = null
                    )
                }
            },
            textStyle = TextStyle(
                color = Color(0xFF359739),
                fontSize = 20.sp,
            ),
            shape = CutCornerShape(12.dp),
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
                viewModel.login(email, password)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isValidEmail && isValidPassword,
            shape = CutCornerShape(12.dp)
        ) {
            Text(text = "로그인", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("signup") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF359739)),
            modifier = Modifier.fillMaxWidth(),
            shape = CutCornerShape(12.dp)
        ) {
            Text(text = "회원가입", fontSize = 20.sp)
        }

    }
}