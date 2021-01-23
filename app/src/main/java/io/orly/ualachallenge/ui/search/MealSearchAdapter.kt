package io.orly.ualachallenge.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.orly.ualachallenge.databinding.ItemMealListBinding
import io.orly.ualachallenge.model.MealListModel

class MealSearchAdapter :
    ListAdapter<MealListModel, MealSearchAdapter.MealViewHolder>(MealDiff()) {

    class MealViewHolder(
        private val binding: ItemMealListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(meal: MealListModel) {
            with(binding) {
                Glide
                    .with(this.root)
                    .load(meal.strMealThumb)
                    .into(this.ivMealThumb)
                this.tvMealName.text = meal.strMeal
                this.tvMealCategory.text = meal.strCategory
                this.cardMeal.setOnClickListener {
                    navigateToDetails(meal.idMeal, meal.strMeal, this.root)
                }
            }
        }

        private fun navigateToDetails(idMeal: String, strMeal: String, view: View) {
            val directions = MealSearchFragmentDirections
                .actionSearchToDetail(strMeal, idMeal)
            view.findNavController().navigate(directions)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder =
        MealViewHolder(
            ItemMealListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

private class MealDiff : DiffUtil.ItemCallback<MealListModel>() {
    override fun areItemsTheSame(oldItem: MealListModel, newItem: MealListModel): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: MealListModel, newItem: MealListModel): Boolean {
        return oldItem == newItem
    }
}