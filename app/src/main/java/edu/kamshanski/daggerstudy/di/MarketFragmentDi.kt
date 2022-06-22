package edu.kamshanski.daggerstudy.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap
import edu.kamshanski.daggerstudy.presentation.MarketViewModel
import edu.kamshanski.daggerstudy.ui.MarketFragment


@FragmentScope
@Subcomponent(modules = [MarketFragmentModule::class])
interface MarketFragmentComponent {

    fun inject(fragment: MarketFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(): MarketFragmentComponent
    }
}

@Module
interface MarketFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(MarketViewModel::class)
    fun provideMarketViewModel(vm: MarketViewModel): ViewModel
}