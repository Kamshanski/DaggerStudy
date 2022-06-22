package edu.kamshanski.daggerstudy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.kamshanski.daggerstudy.model.toy.ToyFabric
import edu.kamshanski.daggerstudy.model.workers.LabourMarket
import edu.kamshanski.daggerstudy.model.workers.Worker
import edu.kamshanski.daggerstudy.util.launch
import edu.kamshanski.daggerstudy.util.shareWhileSubscribed
import edu.kamshanski.daggerstudy.util.stateWhileSubscribed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductionViewModel @Inject constructor(
    private val labourMarket: LabourMarket,
    private val toyFabric: ToyFabric,
): ViewModel() {

    val freeWorkersCount = labourMarket.getFreeWorkersCount().stateWhileSubscribed(viewModelScope, 0)

    val occupiedWorkers = toyFabric.workers.stateWhileSubscribed(viewModelScope, emptyList())

    /** Мапа с колчеством игрушек каждого типа */
    val producedToys = toyFabric.toys
        .map { toys ->
            val types = toys.associateWith { it.type }.values
            val typesCount = types.associateWith { type -> types.count { type == it } }
            typesCount
        }
        .stateWhileSubscribed(viewModelScope, emptyMap())

    private val _message = MutableStateFlow("")
    val message = _message.stateWhileSubscribed(viewModelScope, "")

    fun hireNewWorker() {
        launch {
            val worker = labourMarket.hireSomeone()

            val msg = if (worker != null) {
                toyFabric.addWorker(worker)
                "Чел нанят: $worker"
            } else {
                "Нету свободных людей для найма"
            }

            postForTime(msg)
        }
    }

    fun fireWorker(worker: Worker) {
        launch {
            toyFabric.removeWorker(worker)
            labourMarket.returnWorker(worker)

            _message.emit("Чел уволен: $worker")
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