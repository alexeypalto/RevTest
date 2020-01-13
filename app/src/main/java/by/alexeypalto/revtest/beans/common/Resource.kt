package by.alexeypalto.revtest.beans.common

class Resource<out T> private constructor(
    val status: Status,
    val data: T?,
    val throwable: Throwable?
) {

    companion object {

        fun <T> success(data: T? = null): Resource<T> = Resource(Status.SUCCESS, data, null)

        fun <T> error(throwable: Throwable, data: T? = null): Resource<T> =
            Resource(Status.ERROR, data, throwable)

        fun <T> loading(data: T? = null): Resource<T> = Resource(Status.LOADING, data, null)

    }
}


enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}