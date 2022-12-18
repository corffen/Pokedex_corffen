package com.skydoves.core.data.utils

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.getQueryTextChangeStateFlow(): Flow<String> {
  return callbackFlow {
    val listener = doAfterTextChanged {
      channel.trySend(it.toString())
    }
    awaitClose {
      removeTextChangedListener(listener)
    }
  }
}
