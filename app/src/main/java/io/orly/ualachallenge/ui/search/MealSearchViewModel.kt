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
private const val RANDOM_TIMER_MILLIS = 500L

@FlowPreview
@ExperimentalCoroutinesApi
class MealSearchViewModel(
    private val repository: IMealSearchRepository
) : ViewModel() {
    @ExperimentalCoroutinesApi
    private val queryChannel = ConflatedBroadcastChannel<String>()
    private val randomJob: Job
    val randomMeal = MutableLiveData<BaseResponse<MealListModel>>()
    fun setQuery(strMeal: String) {
        queryChannel.offer(strMeal)
    }

    init {
        randomJob = viewModelScope.launch {
            while (isActive) {
                randomMeal.value = withContext(this.coroutineContext) {
                    repository.getRandomMeal()
                }
                delay(RANDOM_TIMER_MILLIS)
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
}