package com.loci.intern1

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MainViewModel : ViewModel() {

    var auth: FirebaseAuth? = FirebaseAuth.getInstance()




}