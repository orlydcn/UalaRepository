package io.orly.ualachallenge.util

import io.orly.ualachallenge.data.remote.UalaApi
import io.orly.ualachallenge.repository.mealdetail.IMealDetailRepository
import io.orly.ualachallenge.repository.mealdetail.MealDetailRepositoryImp
import io.orly.ualachallenge.repository.mealsearch.IMealSearchRepository
import io.orly.ualachallenge.repository.mealsearch.MealSearchRepositoryImp

interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator()
                }
                return instance!!
            }
        }
    }

    fun getUalaApi(): UalaApi

    fun getSearchMealRepository(): IMealSearchRepository

    fun getDetailMealRepository(): IMealDetailRepository

    open class DefaultServiceLocator : ServiceLocator {
        private val api by lazy {
            UalaApi.create()
        }

        override fun getUalaApi(): UalaApi = api

        override fun getSearchMealRepository(): IMealSearchRepository =
            MealSearchRepositoryImp(api)


        override fun getDetailMealRepository(): IMealDetailRepository =
            MealDetailRepositoryImp(api)

    }
}