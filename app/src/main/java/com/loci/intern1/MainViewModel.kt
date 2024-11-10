package com.loci.intern1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var auth: FirebaseAuth? = null

    private val _isLogin = MutableStateFlow<Boolean>(false)
    val isLogin: StateFlow<Boolean> = _isLogin

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
        viewModelScope.launch {
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task ->
                    _isLogin.value = task.isSuccessful
                }
        }
    }

    fun signUp(email: String, password: String) {
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


}

