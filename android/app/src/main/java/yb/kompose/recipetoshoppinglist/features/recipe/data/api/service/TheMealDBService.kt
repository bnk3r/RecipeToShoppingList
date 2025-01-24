package yb.kompose.recipetoshoppinglist.features.recipe.data.api.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.GetAreas
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.GetCategories
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.GetIngredients
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.GetMealById
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.GetMealByName
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.GetMealCategoriesDetailed
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.GetMealsByAreaFilter
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.GetMealsByCategoryFilter
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.GetMealsByFirstLetter
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.GetMealsByIngredientFilter
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.GetRandomMeal

interface TheMealDBService {

    @GET("search.php")
    suspend fun getMealsByName(@Query("s") name: String): Response<GetMealByName>

    @GET("search.php")
    suspend fun getMealsByFirstLetter(@Query("f") letter: String): Response<GetMealsByFirstLetter>

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): Response<GetMealById>

    @GET("random.php")
    suspend fun getRandomMeal(): Response<GetRandomMeal>

    @GET("categories.php")
    suspend fun getMealCategoriesDetailed(): Response<GetMealCategoriesDetailed>

    @GET("list.php?c=list")
    suspend fun getCategories(): Response<GetCategories>

    @GET("list.php?a=list")
    suspend fun getAreas(): Response<GetAreas>

    @GET("list.php?i=list")
    suspend fun getIngredients(): Response<GetIngredients>

    @GET("filter.php")
    suspend fun getMealsByIngredientFilter(@Query("i") ingredient: String): Response<GetMealsByIngredientFilter>

    @GET("filter.php")
    suspend fun getMealsByCategoryFilter(@Query("c") filter: String): Response<GetMealsByCategoryFilter>

    @GET("filter.php")
    suspend fun getMealsByAreaFilter(@Query("a") area: String): Response<GetMealsByAreaFilter>

}