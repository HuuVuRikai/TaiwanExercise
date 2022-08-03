package com.rikai.taiwanexercise.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey()
    @SerializedName("id")
    var id: String,
    @SerializedName("login")
    var login: String,
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @SerializedName("site_admin")
    var siteAdmin: Boolean,
): Serializable
