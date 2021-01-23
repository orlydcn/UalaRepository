package io.orly.ualachallenge.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface UalaApi {
    @GET(ApiEndpoint.MEAL_LIST_SEARCH)
    suspend fun fetchMealList(
        @Query("strMeal") strMeal: String
    )
}