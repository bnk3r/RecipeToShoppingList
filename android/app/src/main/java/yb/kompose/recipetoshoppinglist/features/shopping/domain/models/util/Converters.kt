package yb.kompose.recipetoshoppinglist.features.shopping.domain.models.util

import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListWithIngredients
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient


fun UiShoppingList.toEntity(): ShoppingListWithIngredients =
    ShoppingListWithIngredients(
        shoppingList = ShoppingList(
            id = id,
            updated = updatedDate
        ),
        ingredients = ingredients.toEntity()
    )

fun UiShoppingListIngredient.toEntity(): ShoppingListIngredient =
    ShoppingListIngredient(
        id = id,
        shoppingListId = shoppingListId,
        name = name,
        amount = amount,
        unit = unit,
        imageUrl = imageUrl
    )

fun List<UiShoppingListIngredient>.toEntity(): List<ShoppingListIngredient> =
    map { uiIngredient ->
        ShoppingListIngredient(
            id = uiIngredient.id,
            shoppingListId = uiIngredient.shoppingListId,
            name = uiIngredient.name,
            amount = uiIngredient.amount,
            unit = uiIngredient.unit,
            imageUrl = uiIngredient.imageUrl
        )
    }

fun ShoppingListWithIngredients.toUiModel(): UiShoppingList =
    UiShoppingList(
        id = shoppingList.id,
        updatedDate = shoppingList.updated,
        ingredients = ingredients.toUiModel()
    )

fun List<ShoppingListIngredient>.toUiModel(): List<UiShoppingListIngredient> =
    map { ingredient ->
        UiShoppingListIngredient(
            id = ingredient.id,
            shoppingListId = ingredient.shoppingListId,
            name = ingredient.name,
            amount = ingredient.amount,
            unit = ingredient.unit,
            imageUrl = ingredient.imageUrl
        )
    }

fun UiIngredient.toShoppingIngredient(shoppingList: UiShoppingList? = null): UiShoppingListIngredient =
    UiShoppingListIngredient(
        id = 0,
        shoppingListId = shoppingList?.id ?: -1,
        name = name,
        amount = parseAmount(),
        unit = parseMeasure(),
        imageUrl = imgUrl
    )

fun UiIngredient.parseAmount(): Double = 0.0

fun UiIngredient.parseMeasure(): MeasureUnit = MeasureUnit.BLANK // TODO