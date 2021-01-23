package io.orly.ualachallenge.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.orly.ualachallenge.repository.mealdetail.IMealDetailRepository
import io.orly.ualachallenge.repository.mealsearch.IMealSearchRepository
import io.orly.ualachallenge.ui.search.MealSearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class MealDetailViewModelFactory(
    private val repository: IMealDetailRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MealDetailViewModel(repository) as T
    }
}