package io.orly.ualachallenge.data.remote

object ApiEndpoint {
    const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    const val MEAL_LIST_SEARCH = "search.php?s={strMeal}"
    const val MEAL_DETAIL = "lookup.php?i={idMeal}"
    const val MEAL_RANDOM = "random.php"
}