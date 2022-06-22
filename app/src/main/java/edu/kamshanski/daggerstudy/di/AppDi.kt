package edu.kamshanski.daggerstudy.di

import android.content.Context
import com.github.terrakok.cicerone.Cicerone
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import edu.kamshanski.daggerstudy.App
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
    ],
)
interface AppComponent {

    fun inject(app: App)

    fun activityComponent(): ActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }
}

@Module(subcomponents = [
    ActivityComponent::class
])
abstract class AppModule {

    companion object {

        @Provides
        @Singleton
        fun provideCicerone() = Cicerone.create()
    }
}