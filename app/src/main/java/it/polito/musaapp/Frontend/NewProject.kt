package it.polito.musaapp.Frontend

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.MutableData
import com.google.firebase.database.database
import it.polito.musaapp.Backend.CreateNewProject
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.setRoute
import it.polito.musaapp.R
import it.polito.musaapp.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProject(navController: NavController, vm:MusaViewModel){
    val context= LocalContext.current
    var i by remember{
        mutableStateOf(0)
    }
    //i= GetCounterProgetti()
    var filledName by remember {
        mutableStateOf("")
    }
    var filledDescription by remember {
        mutableStateOf("")
    }

    var filledCategory by remember {
        mutableStateOf("")
    }
    Box(
        modifier= Modifier
            .fillMaxSize()
            .padding(top = 120.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
    ){
        Box( //box effettivo
            modifier= Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 10.dp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(20.dp)
                )
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier= Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
            ){
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close",
                    tint= MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(44.dp)
                        .align(Alignment.End)
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

                Text(
                    text= "Nuovo progetto personale",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier= Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = filledName,
                    onValueChange = {
                        filledName = it
                    },
                    shape = RoundedCornerShape(15.dp),
                    placeholder =
                    { Text(text = "Nome progetto",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF775c15)
                    )
                    },
                    modifier = Modifier
                        .border(
                            5.dp,
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(15.dp)
                        )
                        .fillMaxWidth(),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        textColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))

                filledCategory=CategoryDropdownProjects(vm)


                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = filledDescription,
                    onValueChange = {
                        filledDescription = it
                    },
                    shape = RoundedCornerShape(15.dp),
                    placeholder =
                    { Text(text = "Descrizione",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF775c15)
                    )
                    },
                    modifier = Modifier
                        .border(
                            5.dp,
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(15.dp)
                        )
                        .fillMaxWidth(),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        textColor = MaterialTheme.colorScheme.onPrimary
                    )
                )



                Button(
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .width(160.dp),
                    onClick = {
                        i= GetCounterProgetti()
                        CreateNewProject(filledName, filledCategory, filledDescription, vm, i)
                        navController.navigate(Screens.ProjectPage.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ){
                    Text(
                        text= "AVVIA",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownProjects(vm: MusaViewModel) : String{
    val context = LocalContext.current
    val categoryDisegno = arrayOf("Pittura", "Animazione", "Modellazione 3D")
    val categoryMusica = arrayOf("Canto", "Colonna sonora", "Sound design")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var categoryToUse =categoryMusica

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    if(vm.category.value!!.equals("Disegno")){
        categoryToUse=categoryDisegno
    }



    val icon = if (expanded){
        painterResource(id = R.drawable.frecciasopra)
    } else painterResource(id = R.drawable.frecciasotto)

    Column(modifier = Modifier.fillMaxWidth()){
        OutlinedTextField(
            shape = RoundedCornerShape(15.dp),
            value = selectedText,
            onValueChange = { selectedText = it},
            readOnly = true,
            placeholder =
            { Text(text = "Categoria",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF775c15)
            ) },
            trailingIcon = {
                Icon(icon , "", tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .size(20.dp))
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTrailingIconColor = Color(0xFF775c15)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFiledSize = coordinates.size.toSize()
                }
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(15.dp))
                .border(5.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(15.dp))
        )
        DropdownMenu(
            offset = DpOffset(0.dp, (8).dp),
            modifier = Modifier
                .width(with(LocalDensity.current) { textFiledSize.width.toDp() })
                .background(MaterialTheme.colorScheme.secondary)
                .border(5.dp, MaterialTheme.colorScheme.primaryContainer),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            for (index in categoryToUse.indices) {
                val item = categoryToUse[index]
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        ) },
                    onClick = {
                        selectedText = item

                        expanded = false
                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
                if (index < categoryToUse.size - 1){
                    Divider(thickness = 3.dp, color = Color(0x1A001219))
                }

            }
        }
    }
    return selectedText
}


fun GetCounterProgetti(): Int{
    var i=0
    val myRef = Firebase.database.getReference("Progetti").child("CounterProgetti")
    myRef.get().addOnSuccessListener {

         i=it.value.toString().toInt()
        Log.d("COUNTERPROGETTI", "valori ${i}");
    }.addOnFailureListener {
        Log.d("COUNTERPROGETTI", "Error", it);
    }
    return i
}