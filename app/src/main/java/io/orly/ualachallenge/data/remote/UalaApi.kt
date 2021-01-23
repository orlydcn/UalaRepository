package io.orly.ualachallenge.data.remote

import io.orly.ualachallenge.model.BaseResponse
import io.orly.ualachallenge.model.MealListModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UalaApi {
    @GET(ApiEndpoint.MEAL_LIST_SEARCH)
    suspend fun fetchMealList(
        @Query("strMeal") strMeal: String
    ): BaseResponse<MealListModel>

    companion object {
        fun create(): UalaApi {
            return Retrofit
                .Builder()
                .baseUrl(ApiEndpoint.BASE_URL)
                .client(HttpClientProvider.httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UalaApi::class.java)
        }
    }
}