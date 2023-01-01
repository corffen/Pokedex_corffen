package com.skydoves.pokedex.ui.flow

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FlowBindViewModel @Inject constructor(
  private val apiHelper: ApiHelperImpl,
  private val dbHelper: DatabaseHelperImpl,
  @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : BindingViewModel() {


  @get:Bindable
  var isLoading: Boolean by bindingProperty(false)
    private set
  private val _users = MutableStateFlow<List<ApiUser>>(emptyList())

  @get:Bindable
  val users: List<ApiUser> by _users.asBindingProperty()

  init {
    fetchUsersWithSingleNetwork()
  }
  private fun fetchUsersWithSingleNetwork() {
    viewModelScope.launch {
      apiHelper.getUsers()
        .onStart {
          isLoading = true
        }.onCompletion {
          isLoading = false
        }
        .flowOn(ioDispatcher)
        .collect {
          _users.value = it
        }
    }
  }

}
