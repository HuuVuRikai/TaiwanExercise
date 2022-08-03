package com.rikai.taiwanexercise.databases

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rikai.taiwanexercise.models.User

@Dao
interface UserDao {
    @Query("SELECT * from user WHERE id = :id")
    suspend fun get(id: String): User

    @Query("DELETE FROM user")
    suspend fun clear()

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAll(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( users: List<User>)
}