package io.orly.ualachallenge.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import io.orly.ualachallenge.model.BaseResponse
import io.orly.ualachallenge.model.MealListModel
import io.orly.ualachallenge.repository.mealsearch.IMealSearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext

private const val SEARCH_DELAY_MILLIS = 300L
private const val MIN_QUERY_LENGTH = 2

@FlowPreview
@ExperimentalCoroutinesApi
class MealSearchViewModel(
    private val repository: IMealSearchRepository
) : ViewModel() {
    @ExperimentalCoroutinesApi
    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)


    private val _meals = queryChannel
        .asFlow()
        .debounce(SEARCH_DELAY_MILLIS)
        .mapLatest {
            if (it.length >= MIN_QUERY_LENGTH) {
                getMeals(it)
            } else {
                BaseResponse(emptyList())
            }
        }
        .catch {

        }

    val meals = _meals.asLiveData()

    private suspend fun getMeals(strMeal: String): BaseResponse<MealListModel> {
        return withContext(viewModelScope.coroutineContext) {
            repository.searchMealRemote(strMeal)
        }
    }
}