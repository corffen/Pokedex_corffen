package com.skydoves.pokedex.core.network.api

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

  override fun getUsers() = flow { emit(apiService.getUsers()) }

  override fun getMoreUsers() = flow { emit(apiService.getMoreUsers()) }

  override fun getUsersWithError() = flow { emit(apiService.getUsersWithError()) }

}