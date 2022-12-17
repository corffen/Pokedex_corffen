package com.skydoves.pokedex.ui.flow

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.skydoves.pokedex.core.model.ApiUser
import com.skydoves.pokedex.core.model.Resource
import com.skydoves.pokedex.core.network.api.ApiHelperImpl
import com.skydoves.pokedex.navigator.Screens
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowViewModel @AssistedInject constructor(
  private val apiHelper: ApiHelperImpl,
  @Assisted private val type: String) : BindingViewModel() {

  @get:Bindable
  var isLoading: Boolean by bindingProperty(true)
    private set

  private val _users = MutableStateFlow<Resource<List<ApiUser>>>(Resource.loading())

  val users: StateFlow<Resource<List<ApiUser>>> = _users

  init {
    if (type == Screens.FLOWS_SINGLE_NETWORK.name) {
      fetchUsersWithSingleNetwork()
    } else if (type == Screens.FLOWS_SERIES_NETWORK.name) {
      fetchUsersWithSeriesNetwork()
    }
  }

  private fun fetchUsersWithSingleNetwork() {
    viewModelScope.launch {
      _users.value = Resource.loading()
      apiHelper.getUsers()
        .flowOn(Dispatchers.IO)
        .catch { e ->
          _users.value = Resource.error(e.toString())
        }.simpleLife()
        .collect {
          _users.value = Resource.success(it)
        }
    }
  }


  fun fetchUsersWithSeriesNetwork() {
    viewModelScope.launch {
      _users.value = Resource.loading()
      val allUsersFromApi = mutableListOf<ApiUser>()
      apiHelper.getUsers()
        .flatMapConcat { usersFromApi ->
          allUsersFromApi.addAll(usersFromApi)
          apiHelper.getMoreUsers()
        }
        .flowOn(Dispatchers.IO)
        .simpleLife()
        .catch { e ->
          _users.value = Resource.error(e.toString())
        }
        .collect { moreUsersFromApi ->
          allUsersFromApi.addAll(moreUsersFromApi)
          _users.value = Resource.success(allUsersFromApi)
        }
    }
  }

  private fun <T> Flow<T>.simpleLife(): Flow<T> {
    return this.onStart {
      isLoading = true
    }.onCompletion {
      isLoading = false
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