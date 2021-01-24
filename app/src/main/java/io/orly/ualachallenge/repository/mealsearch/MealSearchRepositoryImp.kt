package io.orly.ualachallenge.repository.mealsearch

import io.orly.ualachallenge.data.remote.UalaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MealSearchRepositoryImp(
    private val apiService: UalaApi
) : IMealSearchRepository {
    override suspend fun searchMealRemote(strMeal: String) = withContext(Dispatchers.IO) {
        apiService.fetchMealList(strMeal)
    }

    override suspend fun getRandomMeal() = withContext(Dispatchers.IO) {
        apiService.fetchRandomMeal()
    }

}