package by.alexeypalto.revtest.domain.entity

data class Rate(
    val rateCode: String,
    val rateValue: Double
) {
    constructor(pair: Pair<String, Double>) : this(rateCode = pair.first, rateValue = pair.second)
}