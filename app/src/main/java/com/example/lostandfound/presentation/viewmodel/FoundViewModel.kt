package com.example.lostandfound.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lostandfound.domain.interactors.FoundInteractor
import com.example.lostandfound.presentation.view.recyclerview.found.FoundModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoundViewModel @Inject constructor(
    private val interactor: FoundInteractor
) : ViewModel() {
    private lateinit var viewModelJob: Job
    private val broadcast = ConflatedBroadcastChannel<String>()

    private var _founds = MutableLiveData<List<FoundModel>>()
    val founds: LiveData<List<FoundModel>>
        get() = _founds

    init {
        loadFounds()
    }

    fun loadFounds() {
        viewModelJob = viewModelScope.launch {
            val db: FirebaseDatabase = FirebaseDatabase.getInstance()
            val ref = db.getReference("founds")
            val founds: MutableList<FoundModel> = ArrayList()
            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (found in snapshot.children) {
                        founds.add(found.getValue(FoundModel::class.java)!!)
                    }
                    _founds.postValue(founds)
                }

            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}