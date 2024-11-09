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


}

