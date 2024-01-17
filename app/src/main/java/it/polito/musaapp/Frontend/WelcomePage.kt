package it.polito.musaapp.Frontend

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.StartOffsetType.Companion.Delay
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Screens

@Composable
fun WelcomePage(navController: NavController, vm: MusaViewModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
    {
        Text("Siamo felici di vederti qui!")
        Indicator()
        Text("Attendi, stiamo lavorando per te...")

        MoveToRightPage(navController, vm)
    }

}

@Composable
fun Indicator(
    size: Dp = 32.dp, // indicator size
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth //width of cicle and ar lines
) {
    CircularProgressIndicator(
        modifier = Modifier.drawBehind {
            drawCircle(
                Color.Red,
                radius = 10f,
                style = Stroke(strokeWidth.toPx())
            )
        },
        color = Color.LightGray,
        strokeWidth = strokeWidth
    )
}

fun MoveToRightPage(navController: NavController, vm: MusaViewModel) {
    val myRef = Firebase.database.getReference("UtenteGiaRegistrato");
    myRef.get().addOnSuccessListener {
        Log.d("FORM", "valori ${it.value}");
            if (it.value == true) {
                navController.navigate(Screens.HelpPage.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true

                }
                vm.setRegistered(true)
            }
            else {
            navController.navigate(Screens.FormStart.name) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
                }
                vm.setRegistered(false)
            }
        }.addOnFailureListener {
            Log.d("FORM", "Error", it);
        }
}