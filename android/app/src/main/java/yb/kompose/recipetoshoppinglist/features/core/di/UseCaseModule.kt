package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase

fun provideGetRecipeCategoriesUseCase(recipeRepository: RecipeRepository) =
    GetRecipeCategoriesUseCase(recipeRepository)

val useCaseModule = module {
    factory { provideGetRecipeCategoriesUseCase(get()) }
}