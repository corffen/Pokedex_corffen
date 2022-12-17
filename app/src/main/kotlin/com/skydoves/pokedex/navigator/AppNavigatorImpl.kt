package com.skydoves.pokedex.navigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.gordon.common.base.utilcode.util.ActivityUtils
import com.skydoves.pokedex.R
import com.skydoves.pokedex.ui.flow.ButtonFragment
import com.skydoves.pokedex.ui.flow.FlowFragment
import com.skydoves.pokedex.ui.main.MainActivity
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {
  override fun navigateTo(screen: Screens) {
    when (screen) {
      Screens.BUTTONS -> replaceFragment(activity, ButtonFragment.newInstance())
      Screens.Main -> ActivityUtils.startActivity(MainActivity::class.java)
      else -> {
        replaceFragment(activity, FlowFragment.newInstance())
      }
    }
  }

  private fun replaceFragment(activity: FragmentActivity, fragment: Fragment) {
    activity.supportFragmentManager.beginTransaction()
      .replace(R.id.main_container, fragment)
      .addToBackStack(fragment::class.java.canonicalName)
      .commit()
  }
}
