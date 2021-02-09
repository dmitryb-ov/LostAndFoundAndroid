package com.example.lostandfound.data.repository

import com.example.lostandfound.data.local.dao.LostDao
import com.example.lostandfound.data.model.LostEntity
import com.example.lostandfound.di.scope.ApplicationScope
import com.example.lostandfound.presentation.view.recyclerview.lost.LostModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ApplicationScope
class LostRepositoryImpl @Inject constructor(
    private var lostDao: LostDao
) : LostRepository {


    override suspend fun getLostItems(): List<LostModel> = withContext(Dispatchers.IO) {
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
            }

        })
        losts
    }

    override suspend fun getLostItem(lost: LostEntity): LostEntity = withContext(Dispatchers.IO) {
        val lostEntity = lostDao.getLost(lost.id)
        lostEntity
    }

    override suspend fun deleteLostItem(lost: LostEntity) = withContext(Dispatchers.IO) {
        lostDao.delete(lost.id)
    }

    override suspend fun addLostItem(lost: LostEntity) = withContext(Dispatchers.IO) {
        lostDao.insert(lost)
    }

//    override suspend fun searchLosts(list: List<LostEntity>): List<LostEntity> =
//        withContext(Dispatchers.IO) {
//            val losts = lostDao.getAllLost()
//            for (lostEntity in losts) {
//                loop@ for(lost in list){
//                    if(lostEntity.id == lost.id){
//
//                    }
//                }
//            }
//        }
}