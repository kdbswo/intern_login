package com.loci.intern1

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class MainViewModel : ViewModel() {

    var auth: FirebaseAuth? = null


    private val _loginSuccess = MutableStateFlow<Boolean>(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val _signUpError = MutableStateFlow<String?>(null)
    val signUpError: StateFlow<String?> = _signUpError

    private val _signUpEmail = MutableStateFlow<String>("")
    val signUpEmail: StateFlow<String> = _signUpEmail

    private val _signUpPassword = MutableStateFlow<String>("")
    val signUpPassword: StateFlow<String> = _signUpPassword

    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: StateFlow<Boolean> = _signUpSuccess

    init {
        auth = FirebaseAuth.getInstance()
    }

    fun login(email: String, password: String) {

        if (email.isBlank() || password.isBlank()) {
            _loginError.value = "이메일과 비밀번호를 입력해 주세요."
            return
        }

        viewModelScope.launch {
            try {
                auth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _loginSuccess.value = true
                        } else {
                            _loginError.value = convertErrorMessage(task.exception?.message)

                            Log.d("error", "${task.exception?.message}")
                        }
                    }

            } catch (e: Exception) {
                _loginError.value = e.message ?: "로그인 할 수 없습니다."
                Log.d("error", "${e.message}")
            }
        }
    }

    fun signUp(email: String, password: String) {

        if (email.isBlank() || password.isBlank()) {
            _signUpError.value = "이메일과 비밀번호를 입력해 주세요."
            return
        }

        viewModelScope.launch {
            try {
                auth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _signUpEmail.value = email
                            _signUpPassword.value = password
                            _signUpSuccess.value = true
                        } else {
                            _signUpError.value = task.exception?.message
                        }
                    }
            } catch (e: Exception) {
                _signUpError.value = e.message ?: "회원가입 할 수 없습니다."
            }
        }
    }

    fun deleteSignUpError() {
        _signUpError.value = null
    }

    fun deleteLoginError() {
        _loginError.value = null
    }

    fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun convertErrorMessage(message: String?): String {
        return when (message) {
            "Given String is empty or null" -> "입력된 값이 비어 있거나 잘못되었습니다."
            "The email address is badly formatted." -> "이메일 형식이 올바르지 않습니다."
            "The supplied auth credential is incorrect, malformed or has expired." -> "아이디나 비밀번호가 잘못되었습니다."

            else -> "로그인 문제가 발생했습니다."
        }
    }


}

