package com.arisejee.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arisejee.app.AriseJeeApp
import com.arisejee.app.data.db.entity.UserEntity
import com.arisejee.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {
    private val userRepo = UserRepository((app as AriseJeeApp).database.userDao())
    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user.asStateFlow()
    init { viewModelScope.launch { userRepo.ensureUserExists(); userRepo.getUser().collectLatest { _user.value = it } } }
    fun updateUsername(name: String) { viewModelScope.launch { userRepo.updateUsername(name) } }
    fun updateProfilePic(uri: String) { viewModelScope.launch { userRepo.updateProfilePic(uri) } }
    fun updateTimetable(uri: String?) { viewModelScope.launch { userRepo.updateTimetable(uri) } }
}
