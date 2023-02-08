package com.skydoves.pokedex.ui.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.skydoves.bindables.BindingFragment
import com.skydoves.pokedex.R
import com.skydoves.pokedex.databinding.FragmentBindFlowBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FlowBindFragment : BindingFragment<FragmentBindFlowBinding>(R.layout.fragment_bind_flow) {

  @Inject
  lateinit var adapter: ApiUserAdapter

  val viewModel: FlowBindViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    super.onCreateView(inflater, container, savedInstanceState)
    return binding {
      vm = viewModel
      adapter = this@FlowBindFragment.adapter
    }.root
  }

  companion object {
    fun newInstance(): FlowBindFragment {
      return FlowBindFragment()
    }
  }
}
