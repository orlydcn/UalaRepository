package io.orly.ualachallenge.repository.mealsearch

import io.orly.ualachallenge.model.BaseResponse
import io.orly.ualachallenge.model.MealListModel

interface IMealSearchRepository {

    suspend fun searchMealRemote(strMeal: String): BaseResponse<MealListModel>
}