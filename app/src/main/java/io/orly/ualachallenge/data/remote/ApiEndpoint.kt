package io.orly.ualachallenge.data.remote

object ApiEndpoint {
    const val MEAL_LIST_SEARCH = "https://www.themealdb.com/api/json/v1/1/search.php?s={strMeal}"
    const val MEAL_DETAIL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i={idMeal}"
    const val MEAL_RANDOM = "https://www.themealdb.com/api/json/v1/1/random.php"
}