package com.example.lostandfound.security

import android.annotation.SuppressLint
import android.util.Base64
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class AESCrypt {
    companion object {

        @SuppressLint("GetInstance")
        fun encrypt(value: String): String {
            val key = generateKey()
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val encryptedByteValue = cipher.doFinal(value.toByteArray(charset("utf-8")))
            return Base64.encodeToString(encryptedByteValue, Base64.DEFAULT)
        }

        @SuppressLint("GetInstance")
        fun decrypt(value: String): String {
            val key = generateKey()
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, key)
            val decryptedValue64 = Base64.decode(value, Base64.DEFAULT)
            val decryptedByteValue = cipher.doFinal(decryptedValue64)
            return String(decryptedByteValue, charset("utf-8"))
        }

        private fun generateKey(): Key = SecretKeySpec(KEY.toByteArray(), ALGORITHM)

        private const val ALGORITHM = "AES"
        private const val KEY = "a706ecc240f138e5f828629421cf4370" //dggdjhdghkjdghdg
    }
}