package edu.kamshanski.daggerstudy.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Market() = FragmentScreen { MarketFragment() }
    fun Production() = FragmentScreen { ProductionFragment() }
}