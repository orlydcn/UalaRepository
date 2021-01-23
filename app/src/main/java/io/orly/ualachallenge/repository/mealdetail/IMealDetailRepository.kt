package io.orly.ualachallenge.repository.mealdetail

import io.orly.ualachallenge.model.BaseResponse
import io.orly.ualachallenge.model.MealDetailModel

interface IMealDetailRepository {
    suspend fun getMealDetail(idMeal: String): BaseResponse<MealDetailModel>
}