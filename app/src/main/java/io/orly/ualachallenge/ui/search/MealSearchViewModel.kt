package io.orly.ualachallenge.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import io.orly.ualachallenge.model.BaseResponse
import io.orly.ualachallenge.model.MealListModel
import io.orly.ualachallenge.repository.mealsearch.IMealSearchRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.transformLatest

private const val SEARCH_DELAY_MILLIS = 500L
private const val MIN_QUERY_LENGTH = 2
const val RANDOM_TIMER_MILLIS = 3000L

@FlowPreview
@ExperimentalCoroutinesApi
class MealSearchViewModel(
    private val repository: IMealSearchRepository
) : ViewModel() {
    @ExperimentalCoroutinesApi
    private val queryChannel = ConflatedBroadcastChannel<String>()
    val randomMeal = MutableLiveData<BaseResponse<MealListModel>>()

    fun setQuery(strMeal: String) {
        queryChannel.offer(strMeal)
    }

    private suspend fun executeRandom() {
        viewModelScope.launch {
            randomMeal.value = withContext(this.coroutineContext) {
                repository.getRandomMeal()
            }
        }
    }

    private val _meals = queryChannel
        .asFlow()
        .debounce(SEARCH_DELAY_MILLIS)
        .transformLatest {
            val result = if (it.length >= MIN_QUERY_LENGTH) {
                getMeals(it)
            } else {
                BaseResponse(emptyList())
            }
            emit(result)
        }
        .catch {
            Log.d("dasd", "dasd")
        }

    val meals = _meals.asLiveData()

    private suspend fun getMeals(strMeal: String): BaseResponse<MealListModel> {
        return withContext(viewModelScope.coroutineContext) {
            repository.searchMealRemote(
                strMeal
            )
        }
    }

    suspend fun getRandomMeals() {
        executeRandom()
    }

}