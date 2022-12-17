package com.skydoves.pokedex.ui.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.bindables.BindingFragment
import com.skydoves.bundler.bundleNonNull
import com.skydoves.pokedex.R
import com.skydoves.pokedex.core.model.Status
import com.skydoves.pokedex.databinding.FragmentFlowBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class FlowFragment : BindingFragment<FragmentFlowBinding>(R.layout.fragment_flow) {

  private val type: String by bundleNonNull("type")

  @set:Inject
  lateinit var factory: FlowViewModel.AssistedFactory

  @Inject
  lateinit var adapter: ApiUserAdapter

  val viewModel: FlowViewModel by viewModels {
    FlowViewModel.provideFactory(factory, type)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    super.onCreateView(inflater, container, savedInstanceState)
    return binding {
      recyclerView.layoutManager = LinearLayoutManager(requireContext())
      recyclerView.adapter = adapter
    }.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
  }

  private fun observer() {
    viewLifecycleOwner.lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.users.collect {
          when (it.status) {
            Status.SUCCESS -> {
              Timber.tag(TAG).i("success:${it.data}")
              adapter.setList(it.data)
            }
            Status.LOADING -> {
              Timber.tag(TAG).i("LOADING:")
            }
            Status.ERROR -> {
              //Handle Error
              Timber.tag(TAG).i("ERROR:${it.message}")
            }
          }
        }
      }
    }
  }

  companion object {
    private const val TAG = "FlowFragment"
    fun newInstance(type: String): FlowFragment {
      return FlowFragment().apply {
        arguments = bundleOf("type" to type)
      }
    }
  }

}
