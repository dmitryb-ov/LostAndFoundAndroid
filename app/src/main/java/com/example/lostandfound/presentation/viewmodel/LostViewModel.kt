package com.example.lostandfound.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lostandfound.domain.interactors.LostInteractor
import com.example.lostandfound.presentation.view.recyclerview.lost.LostModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LostViewModel @Inject constructor(
    private val interactor: LostInteractor
) : ViewModel() {
    private lateinit var viewModelJob: Job
    private val broadcast = ConflatedBroadcastChannel<String>()

    private var _losts = MutableLiveData<List<LostModel>>()
    val losts: LiveData<List<LostModel>>
        get() = _losts

    init {
        loadLosts()
    }

    fun loadLosts() {
//        viewModelJob = viewModelScope.launch {
//            val resp = repository.getLostItems()
//            Log.e("TAG,",resp[0].description)
//            _losts.postValue(resp)
//        }
        viewModelJob = viewModelScope.launch {
            val db: FirebaseDatabase = FirebaseDatabase.getInstance()
            val ref = db.getReference("losts")
            val losts: MutableList<LostModel> = ArrayList()
            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (lost in snapshot.children) {
                        losts.add(lost.getValue(LostModel::class.java)!!)
//                    Log.e("TAG", losts[0].description)
                    }
                    _losts.postValue(losts)
                }

            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}