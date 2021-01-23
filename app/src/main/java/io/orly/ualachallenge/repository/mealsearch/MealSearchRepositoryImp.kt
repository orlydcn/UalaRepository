package io.orly.ualachallenge.repository.mealsearch

import io.orly.ualachallenge.data.remote.UalaApi
import io.orly.ualachallenge.model.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MealSearchRepositoryImp(
    private val apiService: UalaApi
) : IMealSearchRepository {
    override suspend fun searchMealRemote(strMeal: String) = withContext(Dispatchers.IO) {
        try {
            apiService.fetchMealList(strMeal)
        } catch (error: Throwable) {
            BaseResponse(emptyList())
        }
    }


}