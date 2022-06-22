package edu.kamshanski.daggerstudy.di

import dagger.Module
import dagger.Provides
import edu.kamshanski.daggerstudy.model.toy.ToyFabric
import edu.kamshanski.daggerstudy.model.wallet.Wallet
import edu.kamshanski.daggerstudy.model.workers.LabourMarket
import javax.inject.Singleton

@Module
abstract class DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideToyFabric(): ToyFabric = ToyFabric()

        @Provides
        @Singleton
        fun provideWallet(): Wallet = Wallet()

        @Provides
        @Singleton
        fun provideLabourMarket(): LabourMarket = LabourMarket()
    }

}