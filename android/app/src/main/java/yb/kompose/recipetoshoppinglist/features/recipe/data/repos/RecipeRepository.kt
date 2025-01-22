package yb.kompose.recipetoshoppinglist.features.recipe.data.repos

import android.util.Log
import kotlinx.coroutines.flow.Flow
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.Meal
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.service.TheMealDBService
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.CategoryDAO
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.RecipeDAO
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Category
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Recipe

class RecipeRepository(
    private val remoteDataSource: TheMealDBService,
    private val categoryDao: CategoryDAO,
    private val recipeDao: RecipeDAO
) {

    suspend fun getCategories(): Flow<List<Category>> {
        fetchAndSaveCategories()
        return categoryDao.getAllCategories()
    }

    suspend fun getRecipesForCategory(categoryName: String): Flow<List<Recipe>> {
        fetchAndSaveRecipesForCategory(categoryName)
        return recipeDao.getRecipesByCategory(categoryName)
    }

    private suspend fun fetchAndSaveRecipesForCategory(categoryName: String) {
        val response = remoteDataSource.getMealsByCategoryFilter(categoryName)
        if (response.isSuccessful) {
            response.body()?.meals?.let { responseRecipes ->
                 val convertedRecipes = responseRecipes.map { recipe ->
                    val detailedResponse = remoteDataSource.getMealById(recipe.idMeal)
                    if (!detailedResponse.isSuccessful) {
                        throw IllegalArgumentException("Failed to fetch recipe details")
                    }
                    detailedResponse.body()?.meals?.getOrNull(0)?.let { detailedRecipe ->
                        Recipe(
                            id = detailedRecipe.idMeal.toLong(),
                            name = detailedRecipe.strMeal
                                ?: throw IllegalArgumentException("Recipe name cannot be null"),
                            categoryName = detailedRecipe.strCategory,
                            areaName = detailedRecipe.strArea,
                            instructions = detailedRecipe.strInstructions,
                            imageUrl = detailedRecipe.strMealThumb,
                            thumbnailUrl = "${detailedRecipe.strMealThumb}/preview",
                            tags = detailedRecipe.strTags,
                            youtubeVideoUrl = detailedRecipe.strYoutube,
                            ingredients = detailedRecipe.constructIngredients(),
                            articleUrl = detailedRecipe.strSource
                        )
                    } ?: throw IllegalArgumentException("Recipe details cannot be null")
                }
                convertedRecipes.forEach { recipe ->
                    val id = recipeDao.addRecipe(recipe)
                    Log.d("RecipeRepository", "Save recipe : id=$id")
                }
            }
        }
    }

    private suspend fun fetchAndSaveCategories() {
        val response = remoteDataSource.getMealCategoriesDetailed()
        if (response.isSuccessful) {
            response.body()?.categories?.let { responseCategories ->
                responseCategories
                    .map { category ->
                        Category(
                            id = category.idCategory.toLong(),
                            name = category.strCategory
                                ?: throw IllegalArgumentException("Category name cannot be null"),
                            imageUrl = category.strCategoryThumb,
                            description = category.strCategoryDescription,
                            displayOrderNumber = category.idCategory.toInt()
                        )
                    }
                    .forEach { category ->
                        categoryDao.addCategory(category)
                    }
            }
        } else {
            Log.e(
                "RecipeRepository",
                "Error fetching categories: ${response.message()}"
            )
        }
    }

}

fun Meal.constructIngredients(): String {
    var ingredients = ""
    if (strIngredient1 != null && strMeasure1 != null && strIngredient1.isNotBlank() && strMeasure1.isNotBlank()) {
        ingredients += "$strIngredient1:$strMeasure1,"
    }
    if (strIngredient2 != null && strMeasure2 != null && strIngredient2.isNotBlank() && strMeasure2.isNotBlank()) {
        ingredients += "$strIngredient2:$strMeasure2,"
    }
    if (strIngredient3 != null && strMeasure3 != null && strIngredient3.isNotBlank() && strMeasure3.isNotBlank()) {
        ingredients += "$strIngredient3:$strMeasure3,"
    }
    if (strIngredient4 != null && strMeasure4 != null && strIngredient4.isNotBlank() && strMeasure4.isNotBlank()) {
        ingredients += "$strIngredient4:$strMeasure4,"
    }
    if (strIngredient5 != null && strMeasure5 != null && strIngredient5.isNotBlank() && strMeasure5.isNotBlank()) {
        ingredients += "$strIngredient5:$strMeasure5,"
    }
    if (strIngredient6 != null && strMeasure6 != null && strIngredient6.isNotBlank() && strMeasure6.isNotBlank()) {
        ingredients += "$strIngredient6:$strMeasure6,"
    }
    if (strIngredient7 != null && strMeasure7 != null && strIngredient7.isNotBlank() && strMeasure7.isNotBlank()) {
        ingredients += "$strIngredient7:$strMeasure7,"
    }
    if (strIngredient8 != null && strMeasure8 != null && strIngredient8.isNotBlank() && strMeasure8.isNotBlank()) {
        ingredients += "$strIngredient8:$strMeasure8,"
    }
    if (strIngredient9 != null && strMeasure9 != null && strIngredient9.isNotBlank() && strMeasure9.isNotBlank()) {
        ingredients += "$strIngredient9:$strMeasure9,"
    }
    if (strIngredient10 != null && strMeasure10 != null && strIngredient10.isNotBlank() && strMeasure10.isNotBlank()) {
        ingredients += "$strIngredient10:$strMeasure10,"
    }
    if (strIngredient11 != null && strMeasure11 != null && strIngredient11.isNotBlank() && strMeasure11.isNotBlank()) {
        ingredients += "$strIngredient11:$strMeasure11,"
    }
    if (strIngredient12 != null && strMeasure12 != null && strIngredient12.isNotBlank() && strMeasure12.isNotBlank()) {
        ingredients += "$strIngredient12:$strMeasure12,"
    }
    if (strIngredient13 != null && strMeasure13 != null && strIngredient13.isNotBlank() && strMeasure13.isNotBlank()) {
        ingredients += "$strIngredient13:$strMeasure13,"
    }
    if (strIngredient14 != null && strMeasure14 != null && strIngredient14.isNotBlank() && strMeasure14.isNotBlank()) {
        ingredients += "$strIngredient14:$strMeasure14,"
    }
    if (strIngredient15 != null && strMeasure15 != null && strIngredient15.isNotBlank() && strMeasure15.isNotBlank()) {
        ingredients += "$strIngredient15:$strMeasure15,"
    }
    if (strIngredient16 != null && strMeasure16 != null && strIngredient16.isNotBlank() && strMeasure16.isNotBlank()) {
        ingredients += "$strIngredient16:$strMeasure16,"
    }
    if (strIngredient17 != null && strMeasure17 != null && strIngredient17.isNotBlank() && strMeasure17.isNotBlank()) {
        ingredients += "$strIngredient17:$strMeasure17,"
    }
    if (strIngredient18 != null && strMeasure18 != null && strIngredient18.isNotBlank() && strMeasure18.isNotBlank()) {
        ingredients += "$strIngredient18:$strMeasure18,"
    }
    if (strIngredient19 != null && strMeasure19 != null && strIngredient19.isNotBlank() && strMeasure19.isNotBlank()) {
        ingredients += "$strIngredient19:$strMeasure19,"
    }
    if (strIngredient20 != null && strMeasure20 != null && strIngredient20.isNotBlank() && strMeasure20.isNotBlank()) {
        ingredients += "$strIngredient20:$strMeasure20,"
    }
    return ingredients
}