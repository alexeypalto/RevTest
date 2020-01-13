package by.alexeypalto.revtest.presentation.ui

import by.alexeypalto.revtest.presentation.ui.rates.converter.ConverterTabModule
import dagger.Module

@Module(includes = [ConverterTabModule::class])
interface PresentationUIModule