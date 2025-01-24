package yb.kompose.recipetoshoppinglist.features.recipe.data.api.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.area.Areas
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.category.CategoriesShort
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.ingredient.Ingredients
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.category.CategoriesDetailed
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.meal.MealsShort
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.meal.MealsDetailed

interface TheMealDBService {

    @GET("search.php")
    suspend fun getMealsByName(@Query("s") name: String): Response<MealsDetailed>

    @GET("search.php")
    suspend fun getMealsByFirstLetter(@Query("f") letter: String): Response<MealsDetailed>

    @GET("lookup.php")
    suspend fun getMealsById(@Query("i") id: String): Response<MealsDetailed>

    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealsDetailed>

    @GET("categories.php")
    suspend fun getCategoriesDetailed(): Response<CategoriesDetailed>

    @GET("list.php?c=list")
    suspend fun getCategoriesShort(): Response<CategoriesShort>

    @GET("list.php?a=list")
    suspend fun getAreas(): Response<Areas>

    @GET("list.php?i=list")
    suspend fun getIngredients(): Response<Ingredients>

    @GET("filter.php")
    suspend fun getMealsByIngredient(@Query("i") ingredient: String): Response<MealsShort>

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") filter: String): Response<MealsShort>

    @GET("filter.php")
    suspend fun getMealsByArea(@Query("a") area: String): Response<MealsShort>

}