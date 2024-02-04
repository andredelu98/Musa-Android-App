package it.polito.musaapp.Frontend

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.CalculateDueDates
import it.polito.musaapp.Backend.GetTask
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.RefreshVariablesTask
import it.polito.musaapp.R
import it.polito.musaapp.Screens
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListPage(navController: NavController, vm:MusaViewModel){
    RefreshVariablesTask(vm)
    GetTask(vm)

    val taskList by vm.TaskList.observeAsState()
    var taskToDisplay: Int =0
    Log.d("TASKLIST", taskList.toString())
    if(taskList!=null) {
        CalculateDueDates(vm)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier= Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 16.dp)
            ){
                Box(modifier = Modifier.size(35.dp))

                Image(
                    painter = painterResource(id = R.drawable.loghetto),
                    contentDescription = null,
                    modifier = Modifier.size(85.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.pencil),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).clickable {
                        vm.setPreviousScreen(Screens.TaskListPage)
                        navController.navigate(Screens.ModifyPlanExercise.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            Text(
                text = "I TUOI ESERCIZI",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 26.dp, start = 36.dp, end = 36.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                if(taskList!!.count()>(vm.weeksEx.value!!*vm.daysEx.value!!)){
                    taskToDisplay=(vm.weeksEx.value!!*vm.daysEx.value!!)
                }
                else{
                    taskToDisplay=taskList!!.count()
                }
                for (i in vm.taskCompleted.value!! until taskToDisplay) {
                    //vm.setNextTask(i)
                    //vm.setTaskCounter(i)
                    Card(
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor= MaterialTheme.colorScheme.onPrimary,
                        ),
                        border = BorderStroke(5.dp, MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.clickable {
                            // Log.d("I_TASK", "${i+1}.toString()")
                            vm.setTaskCounter(i+1)
                            vm.setNextTask(i)
                            // Log.d("I_TASK", "next ${vm.nextTask.value}")
                            navController.navigate(Screens.TaskPage.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .height(90.dp)
                                .fillMaxWidth()
                        ){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${i+1}",
                                        style = MaterialTheme.typography.headlineMedium,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .offset(x = 0.dp, y = 3.dp)
                                    )
                                }
                                val truncatedText = if (vm.TaskList.value!!.get(i).length > 35) {
                                    "${vm.TaskList.value!!.get(i).take(45)}..."
                                } else {
                                    vm.TaskList.value!!.get(i)
                                }
                                Text(
                                    text = truncatedText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.width(230.dp)
                                )
                            }
                            if(vm.taskDueDate.isInitialized)
                                Text(
                                    text = vm.taskDueDate.value!!.get(i),
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.align(Alignment.BottomEnd)
                                )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }





    }
}