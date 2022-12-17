package com.skydoves.pokedex.core.network.api

import com.skydoves.pokedex.core.model.ApiUser
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
  fun getUsers(): Flow<List<ApiUser>>

  fun getMoreUsers(): Flow<List<ApiUser>>

  fun getUsersWithError(): Flow<List<ApiUser>>
}