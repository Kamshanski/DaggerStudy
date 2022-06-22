package edu.kamshanski.daggerstudy.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap
import edu.kamshanski.daggerstudy.presentation.ProductionViewModel
import edu.kamshanski.daggerstudy.ui.ProductionFragment


@FragmentScope
@Subcomponent(modules = [ProductionFragmentModule::class])
interface ProductionFragmentComponent {

    fun inject(fragment: ProductionFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(): ProductionFragmentComponent
    }
}

@Module
interface ProductionFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProductionViewModel::class)
    fun provideProductionViewModel(vm: ProductionViewModel): ViewModel
}