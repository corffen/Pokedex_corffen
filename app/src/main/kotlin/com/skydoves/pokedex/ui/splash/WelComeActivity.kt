package com.skydoves.pokedex.ui.splash

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
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


  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    Log.i(TAG, "onSaveInstanceState: ")
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    Log.i(TAG, "onRestoreInstanceState: ")
  }

  override fun onBackPressed() {
    super.onBackPressed()
    if (supportFragmentManager.backStackEntryCount == 0) {
      finish()
    }
  }

  companion object{
    private const val TAG = "WelComeActivity"
  }
}
