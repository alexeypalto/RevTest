package by.alexeypalto.revtest.mapper

import by.alexeypalto.revtest.MockData
import by.alexeypalto.revtest.data.mapper.RatesMapper
import by.alexeypalto.revtest.domain.entity.Rate
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapperTest {

    private val mapperTest = RatesMapper()

    @Test
    fun shouldMapFromModelAsExpected() {
        val ratesBean = MockData.generateRandomRatesBean()
        val ratesObject = mapperTest.mapFromEntity(ratesBean)
        assertEquals(ratesBean.base, ratesObject.base)
        assertEquals(ratesBean.date, ratesObject.date)
        assertEquals(ratesBean.rates.map { Rate(it.key, it.value) }, ratesObject.rates)
    }


}