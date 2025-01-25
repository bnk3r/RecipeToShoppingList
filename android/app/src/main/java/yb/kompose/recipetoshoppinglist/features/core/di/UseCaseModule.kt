package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.DeleteShoppingListIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.DeleteShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.UpdateShoppingListUseCase

fun provideGetRecipeCategoriesUseCase(recipeRepository: RecipeRepository) =
    GetRecipeCategoriesUseCase(recipeRepository)

fun provideGetRecipesForCategoryUseCase(recipeRepository: RecipeRepository) =
    GetRecipesForCategoryUseCase(recipeRepository)

fun provideGetRecipesByQueryUseCase(recipeRepository: RecipeRepository) =
    GetRecipesByQueryUseCase(recipeRepository)

fun provideGetRecipeDetailedUseCase(recipeRepository: RecipeRepository) =
    GetRecipeDetailedUseCase(recipeRepository)

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

val useCaseModule = module {
    factory { provideGetRecipeCategoriesUseCase(get()) }
    factory { provideGetRecipesForCategoryUseCase(get()) }
    factory { provideGetRecipesByQueryUseCase(get()) }
    factory { provideGetRecipeDetailedUseCase(get()) }

    factory { provideGetShoppingListsUseCase(get()) }
    factory { provideAddShoppingListUseCase(get()) }
    factory { provideGetShoppingListUseCase(get()) }
    factory { provideDeleteShoppingListUseCase(get()) }
    factory { provideUpdateShoppingListUseCase(get()) }
    factory { provideDeleteShoppingListIngredientUseCase(get()) }
}