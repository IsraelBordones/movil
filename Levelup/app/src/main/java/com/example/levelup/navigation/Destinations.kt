package com.example.levelup.navigation

// Una 'sealed class' es un tipo especial de clase que nos permite definir
// un conjunto restringido y conocido de subtipos. Es perfecta para las rutas
// de navegación, ya que solo puedes navegar a las pantallas que definas aquí.
sealed class Destinations(
    val route: String // Cada destino tendrá una 'ruta' que es un String.
) {
    // Definimos cada pantalla como un 'object' (una instancia única)
    // que hereda de Destinations.

    object HomeScreen : Destinations("home_screen")
    object FavsScreen : Destinations("favs_screen")
    object SearchScreen : Destinations("search_screen")
    object PerfilScreen : Destinations("perfil_screen")
    object CatalogoScreen : Destinations("catalogo_screen")

    // Si en el futuro necesitas una ruta que acepte un argumento (como un ID de producto),
    // la definirías así:
    // object ProductDetailScreen : Destinations("product_detail_screen/{productId}")
}
