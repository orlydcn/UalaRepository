package io.orly.ualachallenge.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.orly.ualachallenge.databinding.FragmentMealListBinding
import io.orly.ualachallenge.util.ServiceLocator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class MealSearchFragment : Fragment() {
    private lateinit var binding: FragmentMealListBinding
    private val adapter: MealSearchAdapter = MealSearchAdapter()
    private val viewModel: MealSearchViewModel by viewModels {
        MealSearchViewModelFactory(ServiceLocator.instance().getSearchMealRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        subscribeObservers()
        initListeners()
    }


    private fun initUI() {
        binding.rvMealList.adapter = adapter
        binding.search.isActivated = true
    }

    private fun initListeners() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.queryChannel.offer(newText)
                return false
            }
        })
        binding.search.requestFocus()
    }

    private fun subscribeObservers() {
        viewModel.meals.observe(viewLifecycleOwner) {
            adapter.submitList(it.meals ?: emptyList())
        }

    }
}