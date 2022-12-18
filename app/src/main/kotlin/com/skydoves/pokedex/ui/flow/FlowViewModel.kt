package com.skydoves.pokedex.ui.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.pokedex.core.database.entitiy.User
import com.skydoves.pokedex.core.database.entitiy.mapper.asDomain
import com.skydoves.pokedex.core.database.local.DatabaseHelperImpl
import com.skydoves.pokedex.core.model.ApiUser
import com.skydoves.pokedex.core.model.Resource
import com.skydoves.pokedex.core.network.AppDispatchers
import com.skydoves.pokedex.core.network.Dispatcher
import com.skydoves.pokedex.core.network.api.ApiHelperImpl
import com.skydoves.pokedex.navigator.Screens
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowViewModel @AssistedInject constructor(
  private val apiHelper: ApiHelperImpl,
  private val dbHelper: DatabaseHelperImpl,
  @Assisted private val type: String,
  @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : BindingViewModel() {

  init {
    when (type) {
      Screens.FLOWS_SINGLE_NETWORK.name -> {
        fetchUsersWithSingleNetwork()
      }
      Screens.FLOWS_SERIES_NETWORK.name -> {
        fetchUsersWithSeriesNetwork()
      }
      Screens.FLOWS_PARALLEL_NETWORK.name -> {
        fetchUsersWithParallel()
      }
      Screens.FLOWS_DB.name -> {
        fetchUsersWithDb()
      }
    }
  }

  private val _users = MutableStateFlow<Resource<List<ApiUser>>>(Resource.loading())
  val users: StateFlow<Resource<List<ApiUser>>> = _users
//  val userLiveData = _users.asLiveData()
  private fun fetchUsersWithSingleNetwork() {
    viewModelScope.launch {
      _users.value = Resource.loading()
      apiHelper.getUsers()
        .flowOn(ioDispatcher)
        .catch { e ->
          _users.value = Resource.error(e.toString())
        }
        .collect {
          _users.value = Resource.success(it)
        }
    }
  }


  private fun fetchUsersWithSeriesNetwork() {
    viewModelScope.launch {
      _users.value = Resource.loading()
      val allUsersFromApi = mutableListOf<ApiUser>()
      apiHelper.getUsers()
        .flatMapConcat { usersFromApi ->
          allUsersFromApi.addAll(usersFromApi)
          apiHelper.getMoreUsers()
        }
        .flowOn(ioDispatcher)
        .catch { e ->
          _users.value = Resource.error(e.toString())
        }
        .collect { moreUsersFromApi ->
          allUsersFromApi.addAll(moreUsersFromApi)
          _users.value = Resource.success(allUsersFromApi)
        }
    }
  }

  private fun fetchUsersWithParallel() {
    viewModelScope.launch {
      _users.value = Resource.loading()
      apiHelper.getUsers()
        .zip(apiHelper.getMoreUsers()) { usersFromApi, moreUsersFromApi ->
          val allUsersFromApi = mutableListOf<ApiUser>()
          allUsersFromApi.addAll(usersFromApi)
          allUsersFromApi.addAll(moreUsersFromApi)
          return@zip allUsersFromApi
        }
        .flowOn(Dispatchers.IO)
        .catch { e ->
          _users.value = Resource.error(e.toString())
        }
        .collect {
          _users.value = Resource.success(it)
        }
    }
  }

  private fun fetchUsersWithDb() {
    viewModelScope.launch {
      _users.value = Resource.loading()
      dbHelper.getUsers()
        .flatMapConcat { usersFromDb ->
          if (usersFromDb.isEmpty()) {
            return@flatMapConcat apiHelper.getUsers()
              .map { apiUserList ->
                val userList = mutableListOf<User>()
                for (apiUser in apiUserList) {
                  val user = User(
                    apiUser.id,
                    apiUser.name,
                    apiUser.email,
                    apiUser.avatar
                  )
                  userList.add(user)
                }
                userList
              }
              .flatMapConcat { usersToInsertInDB ->
                dbHelper.insertAll(usersToInsertInDB)
                  .flatMapConcat {
                    flow {
                      emit(usersToInsertInDB)
                    }
                  }
              }
          } else {
            return@flatMapConcat flow {
              emit(usersFromDb)
            }
          }
        }
        .flowOn(Dispatchers.IO)
        .catch { e ->
          _users.value = Resource.error(e.toString())
        }
        .collect {
          _users.value = Resource.success(it.map { user ->
            user.asDomain()
          })
        }
    }
  }

  @dagger.assisted.AssistedFactory
  interface AssistedFactory {
    fun create(type: String): FlowViewModel
  }

  companion object {
    fun provideFactory(
      factory: AssistedFactory,
      type: String
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return factory.create(type) as T
      }
    }
  }
}
