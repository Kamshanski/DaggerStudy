package edu.kamshanski.daggerstudy.model.wallet

import edu.kamshanski.daggerstudy.model.toy.Toy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Wallet {

    private var _money: MutableStateFlow<Int> = MutableStateFlow(0)
    val money = _money.asStateFlow()

    /** @return Прибыль от [toys] */
    fun sellToys(toys: List<Toy>): Int {
        val price = toys.sumOf { priceOf(it) }
        _money.value += price
        return price
    }

    private fun priceOf(toy: Toy): Int = (toy.type.ordinal + 1) * 10
}