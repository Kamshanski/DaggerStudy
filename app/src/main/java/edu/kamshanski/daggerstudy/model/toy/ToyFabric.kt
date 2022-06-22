package edu.kamshanski.daggerstudy.model.toy

import edu.kamshanski.daggerstudy.model.workers.Worker
import edu.kamshanski.daggerstudy.util.anyEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ToyFabric @Inject constructor() {

    private val scope = CoroutineScope(Job() + Dispatchers.IO)

    private val productionFlow: Flow<List<Toy>> = flow<List<Toy>> {
        while (true) {
            val productivity = _workers.value.sumOf { it.productivity }
            val newToys = produceToys(productivity)

            _toys.value += newToys

            delay(1500)
        }
    }.stateIn(scope, Eagerly, emptyList())

    private val _workers = MutableStateFlow(listOf<Worker>())
    val workers: StateFlow<List<Worker>> = _workers.asStateFlow()

    private val _toys = MutableStateFlow(setOf<Toy>())
    val toys: StateFlow<Set<Toy>> = _toys.asStateFlow()

    fun getProduction(): Flow<List<Toy>> = flow {
        while (true) {
            val productivity = _workers.value.sumOf { it.productivity }
            val newToys = produceToys(productivity)

            _toys.value += newToys

            delay(1500)
        }
    }

    fun getProductivity(): Flow<Int> = _workers.map(this::calculateProductivity)

    private fun calculateProductivity(workers: List<Worker>): Int =
        workers.sumOf { it.productivity }

    private fun produceToys(amount: Int): List<Toy> =
        MutableList(amount) { Toy(UUID.randomUUID(), anyEnum()) }

    fun addWorker(worker: Worker) {
        _workers.value += worker
    }

    fun removeWorker(worker: Worker) {
        _workers.value -= worker
    }

    fun exportAllToys(): List<Toy> {
        val toysToSell = toys.value
        _toys.value = emptySet()
        return toysToSell.toList()
    }
}