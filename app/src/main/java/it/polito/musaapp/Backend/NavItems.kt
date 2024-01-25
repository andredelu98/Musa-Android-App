package it.polito.musaapp.Backend

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import it.polito.musaapp.Frontend.SelettoreCountGiorniModify
import it.polito.musaapp.Frontend.SelettoreCountSettimaneModify
import it.polito.musaapp.Frontend.SelettoreGiorniModify
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