package com.skydoves.pokedex.ui.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
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

  @Inject
  lateinit var adapter: ButtonAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    super.onCreateView(inflater, container, savedInstanceState)
    return binding {
      recyclerView.layoutManager = LinearLayoutManager(requireContext())
      recyclerView.adapter = adapter
      adapter.setOnItemClickListener { _, _, position ->
        navigator.navigateTo(adapter.getItem(position))
      }
    }.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    adapter.setList(Screens.values().toList())
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
