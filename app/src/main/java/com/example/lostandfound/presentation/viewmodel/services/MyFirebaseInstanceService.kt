package com.example.lostandfound.presentation.viewmodel.services

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.messaging.FirebaseMessaging


class MyFirebaseInstanceService : FirebaseInstanceIdService() {
    private val TAG = "MyFirebaseInstanceServ"

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        FirebaseMessaging.getInstance().subscribeToTopic("all")
        Log.d(TAG, "Refreshed token: $refreshedToken")

        sendRegistrationToServer(refreshedToken)
    }

    private fun sendRegistrationToServer(refreshedToken: String?) {
        Log.d("TOKEN ", refreshedToken.toString())
    }
}