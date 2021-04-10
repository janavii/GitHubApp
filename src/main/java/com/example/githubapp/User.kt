package com.example.githubapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    @field:SerializedName("avatar_url")
    var avatar : String? = "",
    @field:SerializedName("login")
    var username : String? = "",
    @field:SerializedName("name")
    var name : String? = "",
    @field:SerializedName("location")
    var location : String? = "",
    @field:SerializedName("company")
    var company : String? = "",
    @field:SerializedName("public_repos")
    var repository : String? = "",
    @field:SerializedName("followers")
    var followers : String? = "",
    @field:SerializedName("following")
    var following : String? = "",
        ) : Parcelable
