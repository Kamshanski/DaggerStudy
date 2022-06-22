package edu.kamshanski.daggerstudy.di

import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import edu.kamshanski.daggerstudy.MainActivity
import edu.kamshanski.daggerstudy.ViewModelFactory

@Module(
    subcomponents = [
        MarketFragmentComponent::class,
        ProductionFragmentComponent::class,
    ]
)
interface ActivityModule {

    companion object {

        @Provides
        @ActivityScope
        fun provideRouter(cicerone: Cicerone<Router>): Router =
            cicerone.router

        @Provides
        @ActivityScope
        fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder =
            cicerone.getNavigatorHolder()
    }
}

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun marketFragmentComponent(): MarketFragmentComponent.Factory

    fun productionFragmentComponent(): ProductionFragmentComponent.Factory

    fun inject(activity: MainActivity)

    @Subcomponent.Factory
    interface Factory {

        fun create(): ActivityComponent
    }
}