package edu.kamshanski.daggerstudy.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

public fun <T: Any> Flow<T>.stateWhileSubscribed(scope: CoroutineScope, initialValue: T): StateFlow<T> =
    stateIn(scope, SharingStarted.WhileSubscribed(5000), initialValue)

public fun <T: Any> Flow<T>.shareWhileSubscribed(scope: CoroutineScope, replay: Int = 0): SharedFlow<T> =
    shareIn(scope, SharingStarted.WhileSubscribed(5000), replay)

public inline fun <T: ViewModel> T.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    crossinline block: suspend CoroutineScope.() -> Any
): Job = viewModelScope.launch(context, start) { block() }