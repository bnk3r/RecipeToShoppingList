package yb.kompose.recipetoshoppinglist.features.recipe.data.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.ingredient.Ingredient
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.service.TheMealDBService
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.IngredientsDAO

class IngredientsRepository(
    private val apiService: TheMealDBService,
    private val ingredientsDao: IngredientsDAO
) {

    suspend fun fetchIngredients() : List<Ingredient> = withContext(Dispatchers.IO) {
        apiService.getIngredients().body()?.meals ?: emptyList()
    }

    suspend fun saveIngredients(ingredients: List<Ingredient>) : Int = withContext(Dispatchers.IO) {
        val ids = mutableListOf<Long>()
        ingredients.forEach { ingredient ->
            val id = ingredientsDao.addIngredient(ingredient.toEntity())
            if (id != -1L) {
                ids.add(id)
            }
        }
        ids.size
    }

    private fun Ingredient.toEntity() =
        yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Ingredient(
            id = idIngredient?.toLong() ?: throw IllegalArgumentException("Ingredient without id"),
            name = strIngredient ?: throw IllegalArgumentException("Ingredient without name."),
            description = strDescription,
            type = strType
        )

}