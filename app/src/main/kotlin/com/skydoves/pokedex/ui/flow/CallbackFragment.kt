package com.skydoves.pokedex.ui.flow

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gordon.common.base.utilcode.util.ToastUtils
import com.skydoves.bindables.BindingFragment
import com.skydoves.core.data.utils.NetworkMonitor
import com.skydoves.core.data.utils.getQueryTextChangeStateFlow
import com.skydoves.pokedex.R
import com.skydoves.pokedex.databinding.FragmentCallbackBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CallbackFragment : BindingFragment<FragmentCallbackBinding>(R.layout.fragment_callback) {
  @Inject
  lateinit var networkMonitor: NetworkMonitor


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observerNetwork()
    setUpSearchStateFlow()
  }

  private fun observerNetwork() {
    viewLifecycleOwner.lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        networkMonitor.isOnline.map(Boolean::not).stateIn(
          lifecycleScope,
          started = SharingStarted.WhileSubscribed(5000),
          initialValue = false
        ).collect {
          val tips = if (it) "network is unavailable" else "connected!"
          ToastUtils.showShort(tips)
        }
      }
    }
  }

  @OptIn(FlowPreview::class)
  private fun setUpSearchStateFlow() {
    viewLifecycleOwner.lifecycleScope.launch {
      binding.etInput.getQueryTextChangeStateFlow()
        .debounce(300)
        .filter { query ->
          return@filter query.isNotEmpty()
        }
        .distinctUntilChanged()
        .flatMapLatest { query ->
          dataFromNetwork(query)
            .catch {
              emitAll(flowOf(""))
            }
        }
        .flowOn(Dispatchers.Default)
        .collect { result ->
          binding.tvContent.text = result
        }
    }
  }

  /**
   * Simulation of network data
   */
  private fun dataFromNetwork(query: String): Flow<String> {
    return flow {
      delay(200)
      emit(query)
    }
  }

  companion object {
    fun newInstance(): CallbackFragment {
      val args = Bundle()
      val fragment = CallbackFragment()
      fragment.arguments = args
      return fragment
    }
  }
}
