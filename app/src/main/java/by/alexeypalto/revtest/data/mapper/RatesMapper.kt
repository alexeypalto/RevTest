package by.alexeypalto.revtest.data.mapper

import by.alexeypalto.revtest.beans.RatesBean
import by.alexeypalto.revtest.domain.entity.Rate
import by.alexeypalto.revtest.domain.entity.RatesObject

class RatesMapper : Mapper<RatesBean, RatesObject> {

    override fun mapFromEntity(entity: RatesBean): RatesObject {
        return RatesObject(
            base = entity.base,
            date = entity.date,
            rates = entity.rates.map { Rate(it.key, it.value) }
        )
    }

}