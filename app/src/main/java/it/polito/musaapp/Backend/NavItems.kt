package it.polito.musaapp.Backend

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import it.polito.musaapp.R
import it.polito.musaapp.Screens


data class NavItem(
    val label: String,
    val iconId_unselected: Int,
    val iconId_selected: Int,
    var selected: Boolean = false,
    val route: String,
)

val listOfNavItems = listOf(
    NavItem(
        label = "Progetti",
        iconId_unselected = R.drawable.progetti,
        iconId_selected = R.drawable.progetti_pieni,
        route = Screens.ProjectPage.name,
    ),
    NavItem(
        label = "Home",
        iconId_unselected = R.drawable.home,
        iconId_selected = R.drawable.home_piena,
        selected = true,
        route = Screens.HelpPage.name,
    ),

    NavItem(
        label = "Profilo",
        iconId_unselected = R.drawable.profilo,
        iconId_selected = R.drawable.profilo_pieno,
        route = Screens.ProfilePage.name,
    )
)