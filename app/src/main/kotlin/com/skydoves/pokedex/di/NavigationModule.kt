package com.skydoves.pokedex.di

import com.skydoves.pokedex.navigator.AppNavigator
import com.skydoves.pokedex.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

  @Binds
  abstract fun bindNavigator(impl: AppNavigatorImpl): AppNavigator
}
