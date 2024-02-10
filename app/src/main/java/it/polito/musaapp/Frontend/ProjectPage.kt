package it.polito.musaapp.Frontend

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.DeleteSingleProject
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.PopUpCheckIntentions
import it.polito.musaapp.R
import it.polito.musaapp.Screens

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProjectPage(navController: NavController, vm:MusaViewModel){

    val projectList by vm.projectList.observeAsState()
    var count=-1
    val myRef = Firebase.database.getReference("Progetti").child("CounterProgetti")
    if(count==-1){
        myRef.get().addOnSuccessListener {
            //Log.d("PROGETTODB", "valori ${it.value}");
            count = it.value.toString().toInt();
            vm.setCounterProgetti(count)
        }.addOnFailureListener {
            Log.d("PROGETTODB", "Error", it);
        }
    }


    var countCompletati=-1
    val myRef2 = Firebase.database.getReference("Progetti").child("CounterProgettiCompletati")
    if(countCompletati==-1){
        myRef2.get().addOnSuccessListener {
            //Log.d("PROJECTCOMPLETED", "valori ${it.value}");
            countCompletati = it.value.toString().toInt();
            vm.setCounterProgettiCompletati(countCompletati)
        }.addOnFailureListener {
            Log.d("PROJECTCOMPLETED", "Error", it);
        }
    }

    val counterProgetti by vm.counterProgetti.observeAsState()
    val counterProgettiCompletati by vm.counterProgettiCompletati.observeAsState()


    val counterProgettiEliminati by vm.counterProgettiEliminati.observeAsState()

    val opened by vm.popUpOpened.observeAsState()
    val numberToDelete by vm.projectToDelete.observeAsState()

   // Log.d("DELETEPOPUP", "opened: $opened")
    if(opened==true && numberToDelete!=-1){
       // Log.d("DELETEPOPUP", "chiamata popup")
         PopUpCheckIntentions(
            question = "Sei sicuro di voler eliminare il tuo progetto?",
            paragraph = "",
            buttonConfirm = "Si",
            buttonCancel = "No",
            navigationConfirm = Screens.ProjectPage,
            navigationCancel = Screens.ProjectPage,
            navController = navController,
            vm= vm,
            numberToDelete= numberToDelete!!
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 16.dp)
            ) {
                Box(modifier = Modifier.size(35.dp))

                Image(
                    painter = painterResource(id = R.drawable.loghetto),
                    contentDescription = null,
                    modifier = Modifier.size(85.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.archivio),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            vm.setPreviousScreen(Screens.ProjectPage)
                            navController.navigate(Screens.StoricoProgetti.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp, bottom = 24.dp)
                    .verticalScroll(rememberScrollState())
            )
            {
                Text(
                    text = "I TUOI PROGETTI",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
              //  Log.d("LISTA PROGETTI", "projectList ${projectList}, $counterProgetti")
                if (projectList.isNullOrEmpty()
                    || counterProgetti!!-counterProgettiCompletati!!-counterProgettiEliminati!! <= 0){
                    Text(
                        text = "Non hai ancora inserito dei\nprogetti personali",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(480.dp)
                            .padding(top = 8.dp, start = 36.dp, end = 36.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                       // Log.d("LISTAPROGETTI", vm.counterProgetti.value!!.toString())
                        for (i in 0..vm.counterProgetti.value!!-1) {
                            //  Spacer(modifier = Modifier.height(16.dp))
                            vm.setProjectToDelete(i)
                            var openOptions by remember {
                                mutableStateOf(false)
                            }

                            if (vm.projectList.value!![i].status == "creato")
                            {
                                Card(
                                    shape = RoundedCornerShape(15.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                    border = BorderStroke(5.dp, MaterialTheme.colorScheme.primaryContainer),
                                    modifier = Modifier
                                        .clickable {
                                            vm.setProjectToPrint(projectList!![i])
                                            vm.setProjectToPrintCounter(i)
                                            navController.navigate(Screens.SinglePageProject.name) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        //horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .height(60.dp)
                                            .padding(12.dp)
                                    ) {
                                        Text(
                                            text = projectList!!.get(i).name,
                                            style = MaterialTheme.typography.headlineSmall,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .weight(1f)
                                                .offset(x = 0.dp, y = (-3).dp)
                                        )
                                        Box(){
                                            Icon(
                                                Icons.Filled.MoreVert,
                                                contentDescription = "More",
                                                tint = MaterialTheme.colorScheme.onPrimary,
                                                modifier = Modifier
                                                    .size(30.dp)
                                                    .clickable {
                                                        openOptions = !openOptions
                                                    }
                                            )
                                            DropdownMenu(
                                                offset = DpOffset(0.dp, (9).dp),
                                                expanded = openOptions,
                                                onDismissRequest = { openOptions = false },
                                                modifier = Modifier
                                                    .align(Alignment.CenterEnd)
                                                    .background(MaterialTheme.colorScheme.secondary)
                                                    .border(
                                                        5.dp,
                                                        MaterialTheme.colorScheme.primaryContainer
                                                    )
                                            )
                                            {
                                                DropdownMenuItem(
                                                    modifier = Modifier.height(38.dp),
                                                    onClick = {
                                                        openOptions = false
                                                        vm.setfromProjectList(true)
                                                        vm.setProjectToModify(i)
                                                        vm.setProjectToModifyCount(i)
                                                        navController.navigate(Screens.ModifyProject.name) {
                                                            popUpTo(navController.graph.findStartDestination().id) {
                                                                saveState = true
                                                            }
                                                            launchSingleTop = true
                                                            restoreState = true
                                                        }
                                                    },
                                                    text = {
                                                        Box(modifier = Modifier.fillMaxSize()){
                                                            Text(
                                                                text = "Modifica",
                                                                textAlign = TextAlign.Center,
                                                                style = MaterialTheme.typography.bodySmall,
                                                                fontSize = 16.sp,
                                                                modifier = Modifier.align(Alignment.Center)
                                                            )
                                                        }
                                                    },
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Divider(thickness = 3.dp, color = Color(0x1A001219))
                                                Spacer(modifier = Modifier.height(4.dp))
                                                DropdownMenuItem(
                                                    modifier = Modifier.height(38.dp),
                                                    onClick = {
                                                        openOptions = false
                                                        vm.setPopUpOpened(true)

                                                        /*
                                                        DeleteSingleProject(vm, i)
                                                        navController.navigate(Screens.ProjectPage.name) {
                                                            popUpTo(navController.graph.findStartDestination().id) {
                                                                saveState = true
                                                            }
                                                            launchSingleTop = true
                                                            restoreState = true
                                                        }*/
                                                    },
                                                    text = {
                                                        Box(modifier = Modifier.fillMaxSize()){
                                                            Text(
                                                                text = "Elimina",
                                                                textAlign = TextAlign.Center,
                                                                style = MaterialTheme.typography.bodySmall,
                                                                fontSize = 16.sp,
                                                                modifier = Modifier.align(Alignment.Center)
                                                            )
                                                        }
                                                   },
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(60.dp)
                        //.align(Alignment.BottomCenter)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .border(
                            width = 5.dp,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        )
                        .padding(18.dp)
                        .clickable {
                            navController.navigate(Screens.NewProject.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                )
            }
        }
    }
}