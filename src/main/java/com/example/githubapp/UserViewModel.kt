package com.example.githubapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class UserViewModel : ViewModel() {
    companion object {
        private val TAG = UserViewModel::class.java.simpleName
    }

    val detailUser = MutableLiveData<User>()

    fun setDetailUser(users: String) {
        val url = "https://api.github.com/users/$users"
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token ghp_1a379465a89be92d6ec472aeeb7fd52c7ffd4b19")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responObjects = JSONObject(result)
                    val gson = Gson()
                    val user = gson.fromJson(responObjects.toString(), User::class.java)
                    detailUser.postValue(user)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }

        })
    }

    fun getDetailUser(): LiveData<User> {
        return detailUser
    }
}