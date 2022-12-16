package com.skydoves.pokedex.navigator

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.skydoves.pokedex.R
import com.skydoves.pokedex.ui.flow.ButtonFragment
import com.skydoves.pokedex.ui.flow.FlowFragment
import com.skydoves.pokedex.ui.main.MainActivity
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {
  override fun navigateTo(screen: Screens) {
    val fragment = when (screen) {
      Screens.BUTTONS -> ButtonFragment.newInstance()
      Screens.Flows -> FlowFragment.newInstance()
      Screens.Main -> null
    }
    if (fragment != null) {
      activity.supportFragmentManager.beginTransaction()
        .replace(R.id.main_container, fragment)
        .addToBackStack(fragment::class.java.canonicalName)
        .commit()
    }else{
      activity.startActivity(Intent(activity,MainActivity::class.java))
    }
  }
}
