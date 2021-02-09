package com.example.lostandfound.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lostandfound.data.model.User
import com.example.lostandfound.data.model.UserEntity
import com.example.lostandfound.data.repository.ProfileRepository
import com.example.lostandfound.domain.interactors.ProfileInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private var interactor: ProfileInteractor,
    private var repository: ProfileRepository
) : ViewModel() {
    private lateinit var viewModelJob: Job
    private val broadcast = ConflatedBroadcastChannel<String>()

    private var _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity>
        get() = _user

    private var _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    fun saveProfile(profile: User) {
        viewModelJob = viewModelScope.launch {
            interactor.addUser(profile)
        }
    }

    fun getProfiles(): List<User>? {
        viewModelJob = viewModelScope.launch {
            _users.value = interactor.getUsers()
        }
        return _users.value
    }

    fun deleteProfile(profile: User) {
        viewModelJob = viewModelScope.launch {
            interactor.deleteUser(profile)
        }
    }

    fun getCurrentUser(): UserEntity {
        val userEntity = UserEntity()
        _user.postValue(interactor.getCurrentUser(userEntity))
        return interactor.getCurrentUser(userEntity)
    }


//    fun getProfile(profile: User): User? {
//        viewModelJob = viewModelScope.launch {
//            _user.value = interactor.getUser(profile)
//        }
//        return _user.value
//    }

////    fun getProfileByEmail(email: String): UserEntity? {
////        viewModelJob = viewModelScope.launch {
////            val resp = interactor.getUserByEmail(email)
////            _user.postValue(resp)
////        }
////
////        return _user.value
//    }

    fun updateProfile(user: User) {
        viewModelJob = viewModelScope.launch {
            interactor.updateUser(user)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}