package it.polito.musaapp.Backend

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import it.polito.musaapp.Screens

@Composable
fun PopUpCheckIntentions(question: String, paragraph: String, buttonConfirm: String, buttonCancel: String,
                         navigationConfirm: Screens, navigationCancel: Screens,
                         navController:NavController, vm: MusaViewModel){


    val opened by vm.popUpOpened.observeAsState()
    Log.d("POPUPCHECKFUN", opened.toString())
    if(opened == true){
        Box(
            modifier= Modifier
                .fillMaxSize()
                .padding(top = 100.dp, bottom = 300.dp, start = 30.dp, end = 30.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 10.dp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(20.dp)
                )
                .zIndex(10f)

        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier= Modifier
                    .fillMaxSize()
                    .padding(top = 25.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
            ) {
                Text(
                    text=question
                )
                if(paragraph!=""){
                    Text(
                        text = paragraph
                    )
                }
                Row(){
                    Button(
                        onClick = {
                            /*  when(navigationCancel){
                                  Screens.NewProject ->

                              }*/
                            navController.navigate(navigationConfirm.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    {
                        Text(
                            text = buttonConfirm
                        )
                    }
                    Button(
                        onClick = {
                          vm.setPopUpOpened(false)
                        }
                    )
                    {
                        Text(
                            text = buttonCancel
                        )
                    }
                }
            }
        }
    }
}


