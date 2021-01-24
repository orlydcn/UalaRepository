package io.orly.ualachallenge.data.remote

import io.orly.ualachallenge.model.BaseResponse
import io.orly.ualachallenge.model.MealDetailModel
import io.orly.ualachallenge.model.MealListModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UalaApi {
    @GET(ApiEndpoint.MEAL_LIST_SEARCH)
    suspend fun fetchMealList(
        @Query("s") strMeal: String
    ): BaseResponse<MealListModel>

    @GET(ApiEndpoint.MEAL_DETAIL)
    suspend fun fetchMealDetail(
        @Query("i") idMeal: String
    ): BaseResponse<MealDetailModel>

    @GET(ApiEndpoint.MEAL_RANDOM)
    suspend fun fetchRandomMeal(): BaseResponse<MealListModel>

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