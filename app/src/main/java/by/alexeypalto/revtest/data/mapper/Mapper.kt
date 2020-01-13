package by.alexeypalto.revtest.data.mapper

interface Mapper<E, D> {

    fun mapFromEntity(entity: E): D

}