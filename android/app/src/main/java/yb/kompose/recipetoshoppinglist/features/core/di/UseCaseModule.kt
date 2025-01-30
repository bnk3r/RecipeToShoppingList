package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.AddIngredientToShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.DeleteShoppingListIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.DeleteShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetCurrentShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetShoppingListIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.UpdateShoppingListIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.UpdateShoppingListUseCase

fun provideGetRecipeCategoriesUseCase(recipeRepository: RecipeRepository) =
    GetRecipeCategoriesUseCase(recipeRepository)

fun provideGetRecipesForCategoryUseCase(recipeRepository: RecipeRepository) =
    GetRecipesForCategoryUseCase(recipeRepository)

fun provideGetRecipesByQueryUseCase(recipeRepository: RecipeRepository) =
    GetRecipesByQueryUseCase(recipeRepository)

fun provideGetRecipeDetailedUseCase(recipeRepository: RecipeRepository) =
    GetRecipeDetailedUseCase(recipeRepository)

fun provideGetIngredientsUseCase(
    recipeRepository: RecipeRepository
) = GetIngredientsUseCase(recipeRepository)

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
) = DeleteShoppingListIngredientUseCase(shoppingRepository)

fun provideGetCurrentShoppingList(
    shoppingRepository: ShoppingRepository
) = GetCurrentShoppingListUseCase(shoppingRepository)

fun provideAddIngredientToShoppingListUseCase(
    shoppingRepository: ShoppingRepository
) = AddIngredientToShoppingListUseCase(shoppingRepository)

fun provideUpdateShoppingListIngredientUseCase(
    shoppingRepository: ShoppingRepository
) = UpdateShoppingListIngredientUseCase(shoppingRepository)

fun provideGetShoppingListIngredientUseCase(
    shoppingRepository: ShoppingRepository
) = GetShoppingListIngredientUseCase(shoppingRepository)

val useCaseModule = module {
    // RECIPES
    factory { provideGetRecipeCategoriesUseCase(get()) }
    factory { provideGetRecipesForCategoryUseCase(get()) }
    factory { provideGetRecipesByQueryUseCase(get()) }
    factory { provideGetRecipeDetailedUseCase(get()) }

    // RECIPE INGREDIENTS
    factory { provideGetIngredientsUseCase(get()) }

    // SHOPPING LISTS
    factory { provideGetShoppingListsUseCase(get()) }
    factory { provideGetShoppingListUseCase(get()) }
    factory { provideGetCurrentShoppingList(get()) }
    factory { provideAddShoppingListUseCase(get()) }
    factory { provideUpdateShoppingListUseCase(get()) }
    factory { provideDeleteShoppingListUseCase(get()) }

    // SHOPPING INGREDIENTS
    factory { provideGetShoppingListIngredientUseCase(get()) }
    factory { provideAddIngredientToShoppingListUseCase(get()) }
    factory { provideUpdateShoppingListIngredientUseCase(get()) }
    factory { provideDeleteShoppingListIngredientUseCase(get()) }
}