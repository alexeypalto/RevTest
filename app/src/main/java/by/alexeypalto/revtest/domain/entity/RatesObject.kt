package by.alexeypalto.revtest.domain.entity

import java.util.*

data class RatesObject(
    val base: String,
    val date: Date,
    val rates: List<Rate>
)