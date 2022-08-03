package com.rikai.taiwanexercise.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Profile(
    @SerializedName("login")
    var login: String,
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("bio")
    var bio: String,
    @SerializedName("site_admin")
    var siteAdmin: Boolean,
    @SerializedName("location")
    var location: String,
    @SerializedName("blog")
    var blog: String,
)
