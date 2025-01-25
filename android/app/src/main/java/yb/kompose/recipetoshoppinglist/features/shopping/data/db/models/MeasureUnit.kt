package yb.kompose.recipetoshoppinglist.features.shopping.data.db.models

enum class MeasureUnit(val value: String) {
    GRAM("g"),
    KILO("kg"),
    POUND("lb"), // 1lb = 0.453592 kg
    OUNCE("oz"), // 1oz = 28.3495g // also case '175/6oz' // also case '25g/1oz'
    TSP("tsp"), // Tea Spoon // Cuillère à café // 1tsp = 5ml // also case '½ tsp'
    TBS("tbs"), // Table Spoon // Cuillère à soupe // 1tbs = 15ml // also case 'tblsp'
    MILLILITRE("ml"),
    LITER("litre"), // also case 'l'
    GALLON("gal"), // 1gal = 3.7854 liters (US), 4.54609 liters (UK)
    FLUID_OUNCE("fl"), // 1fl = 29,5735ml // also case 'fl oz'
    CUP("cup"), // Tasse // also case 'cups'
    SLICE("slice"), // also case 'slices'
    SPINKLING("spinkling"), // Eclat
    AS_REQUIRED("as required"), // Selon besoin
    PINCH("pinch"), // Pincée
    CHOPPED("chopped"), // Haché
    POD("Pod of"), // Gousse
    GRATED("grated"), // Râpé
    SLICED("sliced"), // Tranché
    LARGE("large"), // Grand
    DRIZZLE("drizzle"), // Bruine
    HANDFUL("handful"), // Poignée
    PACKET("packet"), // Paquet // also case 'packets'
    BOTTLE("bottle"), // Bouteille // also case 'bottles'
    FRY("fry"), // Fri
    DICED("diced"), // Coupé en dés
    SMALL_BUNCH("small bunch"), // Petit bouquet
    GLOVE("glove"), // Gousse // also case 'gloves'
    CAN("can"), // Conserve // also case 'cans'
    JAR("jar"), // Pot // also case 'jars'
    BLANK("") // If not needed
}


