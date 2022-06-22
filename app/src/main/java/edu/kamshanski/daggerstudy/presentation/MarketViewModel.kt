package edu.kamshanski.daggerstudy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.kamshanski.daggerstudy.model.toy.ToyFabric
import edu.kamshanski.daggerstudy.model.wallet.Wallet
import edu.kamshanski.daggerstudy.util.launch
import edu.kamshanski.daggerstudy.util.shareWhileSubscribed
import edu.kamshanski.daggerstudy.util.stateWhileSubscribed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarketViewModel @Inject constructor(
    private val toyFabric: ToyFabric,
    private val wallet: Wallet,
) : ViewModel() {

    val toysCount = toyFabric.toys
        .map { it.size }
        .stateWhileSubscribed(viewModelScope, 0)

    val money = wallet.money.stateWhileSubscribed(viewModelScope, 0)

    private val _message = MutableStateFlow("")
    val message = _message.stateWhileSubscribed(viewModelScope, "")

    fun sellToys() {
        launch {
            val exportedToys = toyFabric.exportAllToys()
            val price = wallet.sellToys(exportedToys)
            val msg = "Проданно на сумму: $price"
            _message.emit(msg)
        }
    }

    private fun postForTime(msg: String, millis: Int = 3500) {
        launch {
            _message.emit(msg)
            delay(millis.toLong())
            if (_message.value == msg) {
                _message.value = ""
            }
        }
    }
}