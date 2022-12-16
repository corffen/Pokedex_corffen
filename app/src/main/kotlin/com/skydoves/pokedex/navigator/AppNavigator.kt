package com.skydoves.pokedex.navigator
enum class Screens {
  BUTTONS,
  Flows,
  Main
}
interface AppNavigator {
  // Navigate to a given screen.
  fun navigateTo(screen: Screens)
}
