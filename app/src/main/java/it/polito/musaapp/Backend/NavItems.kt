package it.polito.musaapp.Backend

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import it.polito.musaapp.Screens


data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

val listOfNavItems = listOf(
    NavItem(
        label = "Progetti",
        icon = Icons.Filled.List,
        route = Screens.ProjectPage.name,
    ),
    NavItem(
        label = "Esercizi",
        icon = Icons.Filled.Home,
        route = Screens.HelpPage.name,
    ),

    NavItem(
        label = "Profilo",
        icon = Icons.Filled.Person,
        route = Screens.ProfilePage.name,
    )
)