package com.umarfarid.complaintbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.google.firebase.auth.FirebaseUser
import com.umarfarid.complaintbox.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.launch

class  MainViewModel : ViewModel() {
    val authRepository = AuthRepository()

    val currentUser = MutableStateFlow<FirebaseUser?>(null)

    init {
        currentUser.value=authRepository.getCurrentUser()
    }

    fun logout(){
        viewModelScope.launch {
            authRepository.logout()
            currentUser.value=null
        }
    }

}