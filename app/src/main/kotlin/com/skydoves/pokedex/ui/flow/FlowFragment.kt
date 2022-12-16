package com.skydoves.pokedex.ui.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import com.skydoves.pokedex.R
import com.skydoves.pokedex.databinding.FragmentFlowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlowFragment : BindingFragment<FragmentFlowBinding>(R.layout.fragment_flow) {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    super.onCreateView(inflater, container, savedInstanceState)
    return binding { }.root
  }

  companion object {
    fun newInstance(): FlowFragment {
      val args = Bundle()
      val fragment = FlowFragment()
      fragment.arguments = args
      return fragment
    }
  }

}
