package com.loci.intern1

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class MainViewModel : ViewModel() {

    var auth: FirebaseAuth? = null


    private val _loginSuccess = MutableStateFlow<Boolean>(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _loginError = MutableSharedFlow<String?>()
    val loginError = _loginError.asSharedFlow()

    private val _signUpError = MutableSharedFlow<String?>()
    val signUpError = _signUpError.asSharedFlow()

    private val _signUpEmail = MutableStateFlow<String>("")
    val signUpEmail: StateFlow<String> = _signUpEmail

    private val _signUpPassword = MutableStateFlow<String>("")
    val signUpPassword: StateFlow<String> = _signUpPassword

    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: StateFlow<Boolean> = _signUpSuccess

    private val _isValidEmail = MutableStateFlow(true)
    val isValidEmail: StateFlow<Boolean> = _isValidEmail

    private val _isValidPassword = MutableStateFlow(true)
    val isValidPassword: StateFlow<Boolean> = _isValidPassword


    init {
        auth = FirebaseAuth.getInstance()
    }

    fun login(email: String, password: String) {

        if (email.isBlank() || password.isBlank()) {
            addLoginError("이메일과 비밀번호를 입력해 주세요.")
            return
        }

        viewModelScope.launch {
            try {
                auth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _loginSuccess.value = true
                        } else {
                            addLoginError(convertLoginErrorMessage(task.exception?.message))
                        }
                    }

            } catch (e: Exception) {
                addLoginError(convertLoginErrorMessage(e.message ?: "로그인 할 수 없습니다."))
            }
        }
    }

    fun signUp(email: String, password: String) {

        if (email.isBlank() || password.isBlank()) {
            addSignUpError("이메일과 비밀번호를 입력해 주세요.")
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
                            addSignUpError(convertSignUpErrorMessage(task.exception?.message))
                            Log.d("error", "${task.exception?.message}")
                        }
                    }
            } catch (e: Exception) {
                addSignUpError(convertSignUpErrorMessage(e.message ?: "회원가입 할 수 없습니다."))
                Log.d("error", "${e.message}")
            }
        }
    }

    private fun addSignUpError(message: String?) {
        viewModelScope.launch {
            _signUpError.emit(message)
        }
    }

    private fun addLoginError(message: String?) {
        viewModelScope.launch {
            _loginError.emit(message)
        }
    }

    fun deleteSignUpError() {
        addSignUpError(null)
    }

    fun deleteLoginError() {
        addLoginError(null)
    }

    fun isValidEmail(email: String) {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        _isValidEmail.value = pattern.matcher(email).matches()
    }

    fun isValidPassword(password: String) {
        _isValidPassword.value = password.length >= 6
    }

    private fun convertLoginErrorMessage(message: String?): String {
        return when (message) {
            "Given String is empty or null" -> "입력된 값이 비어 있거나 잘못되었습니다."
            "The email address is badly formatted." -> "이메일 형식이 올바르지 않습니다."
            "The supplied auth credential is incorrect, malformed or has expired." -> "아이디나 비밀번호가 잘못되었습니다."

            else -> "로그인 문제가 발생했습니다."
        }
    }

    private fun convertSignUpErrorMessage(message: String?): String {
        return when (message) {
            "The email address is already in use by another account." -> "이미 존재하는 이메일 입니다"
            else -> "회원가입 문제가 발생했습니다."
        }
    }


}

