package by.alexeypalto.revtest

import by.alexeypalto.revtest.beans.RatesBean
import by.alexeypalto.revtest.domain.entity.Rate
import by.alexeypalto.revtest.domain.entity.RatesObject
import java.util.*

object MockData {

    fun generateRandomRatesBean(): RatesBean {
        return RatesBean(UUID.randomUUID().toString(), Date(Math.random().toLong()), getRandomMapOfStringDouble())
    }

    fun generateRandomRatesBeanWithBase(baseRate: Rate): RatesBean {
        return RatesBean(baseRate.rateCode, Date(Math.random().toLong()), getRandomMapOfStringDouble())
    }

    fun getRandomMapOfStringDouble(count: Int = 10): Map<String, Double> {
        val map = mutableMapOf<String, Double>()
        repeat(count) {
            map[UUID.randomUUID().toString()] = Math.random()
        }
        return map
    }

    fun generateRandomRatesObject(): RatesObject {
        return RatesObject(UUID.randomUUID().toString(),  Date(Math.random().toLong()),generateRandomProjectModelList())
    }

    fun generateRandomProjectModelList(count: Int = 10): List<Rate> {
        val list = mutableListOf<Rate>()
        repeat(count) {
            list.add(generateRandomRate())
        }
        return list
    }

    fun generateRandomRate(): Rate {
        return Rate(UUID.randomUUID().toString(), Math.random())
    }

}