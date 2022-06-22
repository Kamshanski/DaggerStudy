package edu.kamshanski.daggerstudy.model.workers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LabourMarket @Inject constructor() {

    private val occupiedWorkers: MutableList<Worker> = mutableListOf()

    private val _freeWorkers: MutableStateFlow<List<Worker>> = MutableStateFlow(
        mutableListOf<Worker>().apply {
            add(Worker("Abc", 5))
            add(Worker("Defg", 3))
            add(Worker("Higkl", 2))
            add(Worker("Mnopqr", 4))
            add(Worker("Stuvwxyz", 3))
        }
    )
    private val freeWorkers: StateFlow<List<Worker>> = _freeWorkers

    fun getFreeWorkersCount(): Flow<Int> = freeWorkers.map { it.size }

    fun hireSomeone(): Worker? {
        val workers = _freeWorkers.value
        val worker = workers.getOrNull(0)
        if (worker != null) {
            _freeWorkers.value = workers - worker
            occupiedWorkers.add(worker)
        }
        return worker
    }

    fun returnWorker(worker: Worker) {
        if (worker !in occupiedWorkers) {
            throw IllegalArgumentException("Worker $worker is not registered on market")
        }

        occupiedWorkers.remove(worker)
        _freeWorkers.value += worker
    }
}