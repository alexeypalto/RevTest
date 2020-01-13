package by.alexeypalto.revtest.beans

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class RatesBean(
    @Expose
    @SerializedName("base")
    val base: String,
    @Expose
    @SerializedName("date")
    val date: Date,
    @Expose
    @SerializedName("rates")
    val rates: Map<String, Double>
)