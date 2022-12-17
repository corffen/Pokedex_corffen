package com.skydoves.pokedex.navigator
enum class Screens {
  BUTTONS,
  FLOWS_SINGLE_NETWORK,
  FLOWS_SERIES_NETWORK,
  FLOWS_PARALLEL_NETWORK,
  FLOWS_DB,
  FLOWS_CALLBACK,
  Main
}
interface AppNavigator {
  // Navigate to a given screen.
  fun navigateTo(screen: Screens)
}
