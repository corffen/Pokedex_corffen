package com.skydoves.pokedex.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.skydoves.pokedex.core.database.entitiy.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert
    fun insertAll(users: List<User>)

    @Delete
    fun delete(user: User)

}
