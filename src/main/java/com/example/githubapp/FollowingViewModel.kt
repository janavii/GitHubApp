package com.example.githubapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel :ViewModel() {

    companion object {
        private val TAG = FollowingViewModel::class.java.simpleName
    }

    private val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setFollowing(users: String) {
        val listItemFollowing = ArrayList<User>()
        val url = "https://api.github.com/users/$users/following"
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token ghp_g1a379465a89be92d6ec472aeeb7fd52c7ffd4b19")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObjects = JSONArray(result)
                    for (i in 0 until responseObjects.length()) {
                        val gson = Gson()
                        val user = gson.fromJson(responseObjects.getJSONObject(i).toString(), User::class.java)
                        listItemFollowing.add(user)
                    }
                    listFollowing.postValue(listItemFollowing)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }

        })
    }

    fun getFollowing(): LiveData<ArrayList<User>> {
        return listFollowing
    }
}