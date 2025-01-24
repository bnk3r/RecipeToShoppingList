package yb.kompose.recipetoshoppinglist.features.recipe.data.repos

import androidx.compose.ui.util.fastJoinToString
import kotlinx.coroutines.flow.Flow
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.CategoryDetailed
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.Meal
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.Meal.Companion.MEAL_DB_API_INGREDIENTS_COUNT
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

    suspend fun getRecipesByQuery(query: String): Flow<List<Recipe>> {
        fetchAndSaveRecipesByQuery(query)
        return recipeDao.getRecipesByName(query)
    }

    suspend fun getRecipeDetailed(id: Int): Flow<Recipe?> {
        fetchAndSaveRecipeById(id)
        return recipeDao.getRecipeById(id.toLong())
    }

    private suspend fun fetchAndSaveRecipeById(id: Int) {
        remoteDataSource.getMealById(id.toString()).body()?.meals?.getOrNull(0)
            ?.toDBEntity()
            ?.let { recipeDao.addRecipe(it) }
    }

    private suspend fun fetchAndSaveRecipesByQuery(query: String) {
        remoteDataSource.getMealsByName(query).body()?.meals
            ?.forEach { meal ->
                // Fetch details for each meal
                remoteDataSource.getMealById(meal.idMeal).body()?.meals?.getOrNull(0)
                    ?.toDBEntity()
                    ?.let { recipeDao.addRecipe(it) }
            }
    }


    private suspend fun fetchAndSaveRecipesForCategory(categoryName: String) {
        remoteDataSource.getMealsByCategoryFilter(categoryName).body()?.meals
            ?.forEach { meal ->
                // Fetch details for each meal
                remoteDataSource.getMealById(meal.idMeal).body()?.meals?.getOrNull(0)
                    ?.toDBEntity()
                    ?.let { recipeDao.addRecipe(it) }
            }
    }


    private suspend fun fetchAndSaveCategories() {
        remoteDataSource.getMealCategoriesDetailed().body()?.categories
            ?.forEach { categoryDao.addCategory(it.toDBEntity()) }
    }


    private fun CategoryDetailed.toDBEntity() = Category(
        id = idCategory.toLong(),
        name = strCategory ?: throw IllegalArgumentException("Category name cannot be null"),
        imageUrl = strCategoryThumb,
        description = strCategoryDescription,
        displayOrderNumber = idCategory.toInt()
    )

    private fun Meal.toDBEntity() = Recipe(
        id = idMeal.toLong(),
        name = strMeal ?: throw IllegalArgumentException("Recipe name cannot be null"),
        categoryName = strCategory,
        areaName = strArea,
        instructions = strInstructions,
        imageUrl = strMealThumb,
        thumbnailUrl = "${strMealThumb}/preview",
        tags = strTags,
        youtubeVideoUrl = strYoutube,
        ingredients = ingredientsToFormatedString(),
        articleUrl = strSource
    )

    /**
     * From Class fields (strIngredientX, strMeasureX) to formatted string (Name:Measure,...)
     */
    private fun Meal.ingredientsToFormatedString() = (1..MEAL_DB_API_INGREDIENTS_COUNT)
        .mapIndexed { i, _ ->
            try {
                // Search for fields strIngredient$i & strMeasure$i ($i -> 1 to 20 (inclusive))
                val fieldIngredient = javaClass.getDeclaredField("strIngredient$i")
                val fieldMeasure = javaClass.getDeclaredField("strMeasure$i")
                fieldIngredient.isAccessible = true
                fieldMeasure.isAccessible = true
                val ingredient = fieldIngredient.get(this) as String?
                val measure = fieldMeasure.get(this) as String?
                // formated string with extracted values or null
                when {
                    ingredient != null && measure != null -> "$ingredient:$measure"
                    else -> null
                }
            } catch (e: Exception) {
                null // Just in case to avoid app to get killed for miscellaneous reason
            }
        }
        .filterNotNull()
        .fastJoinToString(",")

}