package com.skydoves.pokedex.core.database.local

import com.skydoves.pokedex.core.database.UserDao
import com.skydoves.pokedex.core.database.entitiy.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val userDao: UserDao) {

  fun getUsers(): Flow<List<User>> = flow {
    emit(userDao.getAll())
  }

  fun insertAll(users: List<User>): Flow<Unit> = flow {
    userDao.insertAll(users)
    emit(Unit)
  }

}
