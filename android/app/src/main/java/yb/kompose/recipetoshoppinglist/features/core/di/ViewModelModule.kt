package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.vimos.CategoryViewModel

fun provideCategoryViewModel(getRecipeCategoriesUseCase: GetRecipeCategoriesUseCase) =
    CategoryViewModel(getRecipeCategoriesUseCase)

val viewModelModule = module {
    viewModel { provideCategoryViewModel(get()) }
}