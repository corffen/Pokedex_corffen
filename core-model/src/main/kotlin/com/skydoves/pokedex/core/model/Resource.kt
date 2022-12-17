package com.skydoves.pokedex.core.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(Status.ERROR, null, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

    }

}

fun <T> Flow<T>.asResult(): Flow<Resource<T>> {
  return this
    .map<T, Resource<T>> {
      Resource.success(it)
    }
    .onStart { emit(Resource.loading()) }
    .catch { emit(Resource.error(it.message ?:"")) }
}