package it.polito.musaapp.Frontend

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.DeleteSingleProject
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.ProjectCompleted
import it.polito.musaapp.R
import it.polito.musaapp.Screens


@Composable
fun SingleProjectPage(navController: NavController, vm: MusaViewModel){
    val project by vm.projectToPrint.observeAsState()
    var openOptions by remember { mutableStateOf(false) }
    var completed=false
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        navController.navigate(Screens.ProjectPage.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.loghetto),
                contentDescription = null,
                modifier = Modifier.size(85.dp)
            )

            Box() {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { openOptions = !openOptions }
                )
                DropdownMenu(
                    offset = DpOffset(0.dp, (9).dp),
                    expanded = openOptions,
                    onDismissRequest = { openOptions = false },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .background(MaterialTheme.colorScheme.secondary)
                        .border(5.dp, MaterialTheme.colorScheme.primaryContainer)
                )
                {
                    DropdownMenuItem(
                        modifier = Modifier.height(38.dp),
                        onClick = {
                            openOptions = false
                            navController.navigate(Screens.ModifyProject.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        text = {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = "Modifica",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .clickable {
                                            openOptions = false
                                            vm.setfromProjectList(false)
                                            vm.projectToPrintCounter.value?.let {
                                                vm.setProjectToModify(it)
                                                vm.setProjectToModifyCount(it)
                                            }
                                            navController.navigate(Screens.ModifyProject.name) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
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
                            Firebase.database.getReference("Progetti").child("CounterProgettiEliminati")
                                .setValue(vm.counterProgettiEliminati.value!!)
                            vm.setCounterProgettiEliminati(vm.counterProgettiEliminati.value!!+1)
                            vm.projectToPrintCounter.value?.let { DeleteSingleProject(vm, it) }
                            navController.navigate(Screens.ProjectPage.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        text = {
                            Box(modifier = Modifier.fillMaxSize()) {
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Text(
                text = project!!.name.uppercase(),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = project!!.category,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ){
                Text(
                    text = project!!.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 35.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ){
                    Button (
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .border(
                                5.dp,
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.shapes.large
                            )
                        ,
                        onClick = {/*TODO*/} //VEDI REFERENCE
                    )
                    {
                        Text(text = "Vedi Reference",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.offset(x = 0.dp, y = (-2).dp)
                        )
                    }
                    Button(
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.width(280.dp),
                        onClick = {
                            //COMPLETATO
                            if(!completed){
                                completed=true
                                ProjectCompleted(vm.projectToPrint.value!!, vm, vm.projectToPrintCounter.value!!)
                                navController.navigate(Screens.ProjectPage.name) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    ){
                        Text(
                            text = "COMPLETATO",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.background,
                        )
                    }
                }
            }


        }
    }

}