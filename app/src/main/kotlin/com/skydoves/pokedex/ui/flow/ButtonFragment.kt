package com.skydoves.pokedex.ui.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import com.skydoves.pokedex.R
import com.skydoves.pokedex.databinding.FragmentButtonBinding
import com.skydoves.pokedex.navigator.AppNavigator
import com.skydoves.pokedex.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ButtonFragment : BindingFragment<FragmentButtonBinding>(R.layout.fragment_button) {

  @Inject
  lateinit var navigator: AppNavigator

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    super.onCreateView(inflater, container, savedInstanceState)
    return binding {
      tv.setOnClickListener {
        navigator.navigateTo(Screens.Flows)
      }
    }.root
  }

  companion object {
    fun newInstance(): ButtonFragment {
      val args = Bundle()
      val fragment = ButtonFragment()
      fragment.arguments = args
      return fragment
    }
  }

}
