package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.CategoriesRepository
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.IngredientsRepository
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.AddIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.DeleteIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.GetIngredientByIdUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.IncreaseIngredientQuantityUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.ReduceIngredientQuantityUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.UpdateIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.DeleteShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.UpdateShoppingListUseCase

fun provideGetRecipeCategoriesUseCase(categoriesRepository: CategoriesRepository) =
    GetRecipeCategoriesUseCase(categoriesRepository)

fun provideGetRecipesForCategoryUseCase(recipeRepository: RecipeRepository) =
    GetRecipesForCategoryUseCase(recipeRepository)

fun provideGetRecipesByQueryUseCase(recipeRepository: RecipeRepository) =
    GetRecipesByQueryUseCase(recipeRepository)

fun provideGetRecipeDetailedUseCase(recipeRepository: RecipeRepository) =
    GetRecipeDetailedUseCase(recipeRepository)

fun provideFetchCategoriesUseCase(
    categoriesRepository: CategoriesRepository
) = FetchAndSaveCategoriesUseCase(categoriesRepository)

fun provideGetIngredientsUseCase(
    recipeRepository: RecipeRepository
) = GetIngredientsUseCase(recipeRepository)

fun provideFetchIngredientsUseCase(
    ingredientsRepository: IngredientsRepository
) = FetchAndSaveIngredientsUseCase(ingredientsRepository)

fun provideGetShoppingListsUseCase(shoppingRepository: ShoppingRepository) =
    GetShoppingListsUseCase(shoppingRepository)

fun provideAddShoppingListUseCase(shoppingRepository: ShoppingRepository) =
    AddShoppingListUseCase(shoppingRepository)

fun provideGetShoppingListUseCase(
    shoppingRepository: ShoppingRepository
) = GetShoppingListUseCase(shoppingRepository)

fun provideDeleteShoppingListUseCase(
    shoppingRepository: ShoppingRepository
) = DeleteShoppingListUseCase(shoppingRepository)

fun provideUpdateShoppingListUseCase(
    shoppingRepository: ShoppingRepository
) = UpdateShoppingListUseCase(shoppingRepository)

fun provideDeleteShoppingListIngredientUseCase(
    shoppingRepository: ShoppingRepository
) = DeleteIngredientUseCase(shoppingRepository)

fun provideAddIngredientToShoppingListUseCase(
    shoppingRepository: ShoppingRepository
) = AddIngredientUseCase(shoppingRepository)

fun provideUpdateShoppingListIngredientUseCase(
    shoppingRepository: ShoppingRepository
) = UpdateIngredientUseCase(shoppingRepository)

fun provideGetShoppingListIngredientUseCase(
    shoppingRepository: ShoppingRepository
) = GetIngredientByIdUseCase(shoppingRepository)

fun provideReduceIngredientQuantityUseCase(
    shoppingRepository: ShoppingRepository
) = ReduceIngredientQuantityUseCase(shoppingRepository)

fun provideIncreaseIngredientQuantityUseCase(
    shoppingRepository: ShoppingRepository
) = IncreaseIngredientQuantityUseCase(shoppingRepository)

val useCaseModule = module {
    // RECIPES
    factory { provideGetRecipeCategoriesUseCase(get()) }
    factory { provideGetRecipesForCategoryUseCase(get()) }
    factory { provideGetRecipesByQueryUseCase(get()) }
    factory { provideGetRecipeDetailedUseCase(get()) }

    // RECIPE CATEGORIES
    factory { provideFetchCategoriesUseCase(get()) }

    // RECIPE INGREDIENTS
    factory { provideGetIngredientsUseCase(get()) }
    factory { provideFetchIngredientsUseCase(get()) }

    // SHOPPING LISTS
    factory { provideGetShoppingListsUseCase(get()) }
    factory { provideGetShoppingListUseCase(get()) }
    factory { provideAddShoppingListUseCase(get()) }
    factory { provideUpdateShoppingListUseCase(get()) }
    factory { provideDeleteShoppingListUseCase(get()) }

    // SHOPPING INGREDIENTS
    factory { provideGetShoppingListIngredientUseCase(get()) }
    factory { provideAddIngredientToShoppingListUseCase(get()) }
    factory { provideUpdateShoppingListIngredientUseCase(get()) }
    factory { provideDeleteShoppingListIngredientUseCase(get()) }
    factory { provideReduceIngredientQuantityUseCase(get()) }
    factory { provideIncreaseIngredientQuantityUseCase(get()) }
}