package io.orly.ualachallenge.repository.mealdetail

import io.orly.ualachallenge.data.remote.UalaApi
import io.orly.ualachallenge.model.BaseResponse
import io.orly.ualachallenge.model.MealDetailModel

class MealDetailRepositoryImp(
    private val apiService: UalaApi
) : IMealDetailRepository {
    override suspend fun getMealDetail(idMeal: String): BaseResponse<MealDetailModel> {
        TODO("Not yet implemented")
    }
}