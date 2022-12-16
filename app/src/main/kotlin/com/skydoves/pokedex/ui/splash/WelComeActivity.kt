package com.skydoves.pokedex.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.pokedex.R
import com.skydoves.pokedex.navigator.AppNavigator
import com.skydoves.pokedex.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelComeActivity : AppCompatActivity() {

  @Inject
  lateinit var navigator: AppNavigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    if (savedInstanceState == null) {
      navigator.navigateTo(Screens.BUTTONS)
    }
  }

  override fun onBackPressed() {
    super.onBackPressed()
    if (supportFragmentManager.backStackEntryCount == 0) {
      finish()
    }
  }
}
