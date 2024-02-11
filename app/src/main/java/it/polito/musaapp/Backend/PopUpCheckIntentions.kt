package it.polito.musaapp.Backend

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Frontend.DeleteProfile
import it.polito.musaapp.Screens

@Composable
fun PopUpCheckIntentions(question: String, paragraph: String, buttonConfirm: String, buttonCancel: String,
                         navigationConfirm: Screens, navigationCancel:Screens,
                         navController:NavController, vm: MusaViewModel,
                         numberToDelete: Int){


    val opened by vm.popUpOpened.observeAsState()
    Log.d("POPUPCHECKFUN", opened.toString())
    if(opened == true){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = false){  }
                .padding(horizontal = 30.dp)
                .zIndex(100f)
        ){
            Box(
                modifier= Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 9.dp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(20.dp)
                    )
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier= Modifier
                        //.fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 24.dp)
                        .background(
                            MaterialTheme.colorScheme.primary
                        )
                ) {
                    Text(
                        text = question,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    if(paragraph!=""){
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = paragraph,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Button(
                            modifier = Modifier.height(45.dp).width(80.dp),
                            shape = MaterialTheme.shapes.large,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            onClick = {

                                when(navigationCancel){
                                    Screens.ProjectPage -> {
                                        Log.d("DELETEPOPUP", "$numberToDelete")
                                        DeleteSingleProject(vm, numberToDelete)
                                        NavigateConfirmed(navController, navigationConfirm)
                                    }
                                    Screens.ModifyPlanExercise->{
                                        DeletePlanExercise(vm)
                                        NavigateConfirmed(navController, navigationConfirm)
                                    }
                                    Screens.ProfilePage -> {
                                        DeleteProfile(vm)
                                        NavigateConfirmed(navController, navigationConfirm)
                                    }
                                    Screens.SinglePageProject -> {
                                        if(vm.projectList.value!![vm.projectToPrintCounter.value!!].status=="creato"){
                                            navController.navigate(Screens.ProjectPage.name) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                        else if(vm.projectList.value!![vm.projectToPrintCounter.value!!].status=="completato"){
                                            vm.setCounterProgettiCompletati(vm.counterProgettiCompletati.value!! - 1)
                                            Firebase.database.getReference("Progetti").child("CounterProgettiCompletati")
                                                .setValue(vm.counterProgettiCompletati.value!!)
                                            navController.navigate(Screens.StoricoProgetti.name) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                        DeleteSingleProject(vm, vm.projectToPrintCounter.value!!)
                                    }
                                    Screens.ModifyProject -> {
                                        if(numberToDelete == 1){
                                            navController.navigate(Screens.ProjectPage.name) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }else{
                                            navController.navigate(Screens.SinglePageProject.name) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    }
                                    else -> NavigateConfirmed(navController, navigationConfirm)
                                }
                                if(navigationConfirm == Screens.HelpPage) setRoute(Screens.HelpPage.name)
                                vm.setPopUpOpened(false)
                            }
                        )
                        {
                            Text(
                                text = buttonConfirm,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.background,
                                textAlign = TextAlign.Center
                            )
                        }
                        Button(
                            modifier = Modifier.height(45.dp).width(80.dp),
                            shape = MaterialTheme.shapes.large,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            onClick = {
                                //Log.d("POPUPCLOSINGB", opened.toString())
                                vm.setPopUpOpened(false)
                                //Log.d("POPUPCLOSINGA", opened.toString())
                            }
                        )
                        {
                            Text(
                                text = buttonCancel,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.background,
                                textAlign = TextAlign.Center

                            )
                        }
                    }
                }
            }
        }

    }
}


fun NavigateConfirmed(navController:NavController, navigationConfirm: Screens){
    navController.navigate(navigationConfirm.name) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}