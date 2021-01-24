package io.orly.ualachallenge.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import io.orly.ualachallenge.databinding.FragmentMealListBinding
import io.orly.ualachallenge.util.ServiceLocator
import kotlinx.coroutines.*

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
        binding.search.setOnClickListener {
            binding.search.isIconified = false
        }
    }

    private fun initListeners() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.setQuery(newText)
                return false
            }
        })
        binding.search.requestFocus()
    }

    private fun subscribeObservers() {
        viewModel.meals.observe(viewLifecycleOwner) {
            adapter.submitList(it.meals ?: emptyList())
        }

        viewModel.randomMeal.observe(viewLifecycleOwner) { result ->
            result.meals?.firstOrNull()?.let { meal ->
                Glide
                    .with(binding.root)
                    .load(meal.strMealThumb)
                    .into(binding.itemRandomMeal.ivMealThumb)
                binding.itemRandomMeal.tvMealName.text = meal.strMeal
                binding.itemRandomMeal.tvMealCategory.text = meal.strCategory
                binding.itemRandomMeal.cardMeal.setOnClickListener {
                    val directions = MealSearchFragmentDirections
                        .actionSearchToDetail(meal.strMeal, meal.idMeal)
                    findNavController().navigate(directions)
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            while (this.isActive) {
                viewModel.getRandomMeals()
                delay(RANDOM_TIMER_MILLIS)
            }
        }
    }
}