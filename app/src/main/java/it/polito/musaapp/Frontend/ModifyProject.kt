package it.polito.musaapp.Frontend

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.MutableData
import com.google.firebase.database.database
import it.polito.musaapp.Backend.CreateNewProject
import it.polito.musaapp.Backend.DeleteSingleProject
import it.polito.musaapp.Backend.ModifySingleProject
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.SingleProject
import it.polito.musaapp.Backend.setRoute
import it.polito.musaapp.R
import it.polito.musaapp.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyProject(navController: NavController, vm:MusaViewModel){
    val context= LocalContext.current
    val fromProjectList by vm.fromProjectList.observeAsState()
    var i by remember{
        mutableStateOf(0)
    }
    //i= GetCounterProgetti()
    var filledName by remember {
        mutableStateOf(vm.projectToModify.value!!.name)
    }
    var filledDescription by remember {
        mutableStateOf(vm.projectToModify.value!!.description)
    }

    var filledCategory by remember {
        mutableStateOf(vm.projectToModify.value!!.category)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .fillMaxSize()
    ){
        if(vm.fromProjectList.value == true){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 16.dp)
                    .alpha(0.3f)
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
                        .clickable {}
                )
            }
        }else{
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 16.dp)
                    .alpha(0.3f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {}
                )
                Image(
                    painter = painterResource(id = R.drawable.loghetto),
                    contentDescription = null,
                    modifier = Modifier.size(85.dp)
                )
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { }
                )
            }
        }
        Box( //box effettivo
            modifier= Modifier
                .fillMaxSize()
                .padding(top = 20.dp, bottom = 30.dp, start = 30.dp, end = 30.dp)
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
                    .padding(top = 25.dp, bottom = 10.dp, start = 25.dp, end = 25.dp)
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
            ){
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close",
                    tint= MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(44.dp)
                        .align(Alignment.End)
                        .clickable {
                            if(vm.fromProjectList.value == true){
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
                )
                Text(
                    text= "Modifica progetto personale",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier= Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = filledName,
                    onValueChange = {
                        filledName = it
                    },
                    shape = RoundedCornerShape(15.dp),
                    placeholder =
                    {
                        if(filledName==""){
                            Text(text = "Nome progetto",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF775c15)
                            )
                        }
                    },
                    modifier = Modifier
                        .border(
                            5.dp,
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(15.dp)
                        )
                        .fillMaxWidth(),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        cursorColor = MaterialTheme.colorScheme.onPrimary,
                        selectionColors = TextSelectionColors(MaterialTheme.colorScheme.onPrimary, MaterialTheme.colorScheme.tertiary),
                        textColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Spacer(modifier = Modifier.height(28.dp))


                filledCategory= CategoryDropdownProjectsModify(vm)

                Spacer(modifier = Modifier.height(28.dp))

                OutlinedTextField(
                    value = filledDescription,
                    onValueChange = {
                        filledDescription = it
                    },
                    shape = RoundedCornerShape(15.dp),
                    placeholder =
                    {
                        if(filledDescription==""){
                            Text(text = "Descrizione",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF775c15)
                            )
                        }
                    },
                    modifier = Modifier
                        .border(
                            5.dp,
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(15.dp)
                        )
                        .fillMaxWidth()
                        .height(165.dp),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        cursorColor = MaterialTheme.colorScheme.onPrimary,
                        selectionColors = TextSelectionColors(MaterialTheme.colorScheme.onPrimary, MaterialTheme.colorScheme.tertiary),
                        textColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .width(150.dp),
                    onClick = {
                        if(vm.fromProjectList.value == true){
                            ModifySingleProject(filledName, if(filledCategory=="") vm.projectToModify.value!!.category else filledCategory, filledDescription, vm, vm.projectToModifyCount.value!!)
                            navController.navigate(Screens.ProjectPage.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                                }
                        }else {
                            ModifySingleProject(
                                filledName,
                                if (filledCategory == "") vm.projectToModify.value!!.category else filledCategory,
                                filledDescription,
                                vm,
                                vm.projectToModifyCount.value!!
                            )
                            navController.navigate(Screens.SinglePageProject.name) {
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
                        text= "SALVA",
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
fun CategoryDropdownProjectsModify(vm: MusaViewModel) : String{
    val context = LocalContext.current
    val categoryDisegno = arrayOf("Pittura", "Animazione", "Modellazione 3D")
    val categoryMusica = arrayOf("Canto", "Colonna sonora", "Sound design")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(vm.projectToModify.value!!.category) }
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
            {
                if(selectedText==""){
                    Text(
                        text = "Categoria",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF775c15)
                    )
                }
            },
            trailingIcon = {
                Icon(icon , "", tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .size(20.dp))
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.secondary,
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

