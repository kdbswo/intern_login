package com.loci.intern1

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLogin = MutableStateFlow(auth.currentUser != null)
    val isLogin: StateFlow<Boolean> = _isLogin

    fun logout() {
        auth.signOut()
        _isLogin.value = false
    }
}