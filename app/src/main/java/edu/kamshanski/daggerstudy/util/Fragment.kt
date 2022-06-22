package edu.kamshanski.daggerstudy.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun Fragment.repeatOnStarted(crossinline block: suspend CoroutineScope.() -> Unit) {

    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.whenResumed {
            block()
        }
    }
}