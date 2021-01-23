package io.orly.ualachallenge.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.orly.ualachallenge.repository.mealsearch.IMealSearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class MealSearchViewModelFactory(
    private val repository: IMealSearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MealSearchViewModel(repository) as T
    }
}