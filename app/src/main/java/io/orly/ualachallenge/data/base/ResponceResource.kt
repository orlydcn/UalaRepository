package io.orly.ualachallenge.data.base

data class ResultResource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): ResultResource<T> {
            return ResultResource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): ResultResource<T> {
            return ResultResource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): ResultResource<T> {
            return ResultResource(Status.LOADING, data, null)
        }
    }
}