package io.orly.ualachallenge.model

data class BaseResponse<T>(
    val meals: List<T>? = emptyList()
)