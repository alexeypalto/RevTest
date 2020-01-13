package by.alexeypalto.revtest.di

import by.alexeypalto.revtest.RevTestApp
import by.alexeypalto.revtest.data.api.ApiServiceModule
import by.alexeypalto.revtest.domain.usecase.UseCaseModule
import by.alexeypalto.revtest.presentation.ui.PresentationUIModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        UseCaseModule::class,
        UseCaseRepositoryModule::class,
        PresentationUIModule::class,
        ApiServiceModule::class,
        MapperModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: RevTestApp): Builder

        fun build(): AppComponent
    }

    fun injectApp(application: RevTestApp)
}