package it.polito.musaapp.Frontend

import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Firebase
import com.google.firebase.database.MutableData
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.R
import it.polito.musaapp.Screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyProfile(navController: NavController, vm: MusaViewModel){
    var filledName by remember {
        mutableStateOf(vm.name.value.toString())
    }
    var filledMail by remember {
        mutableStateOf(vm.mail.value.toString())
    }

    var filledCategory by remember {
        mutableStateOf("")
    }

    var filledLevel by remember {
        mutableStateOf("")
    }

    var filledProfession by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    // Firebase.database.getReference("ModuloStart").child("Categoria").setValue("Disegno");
    // Firebase.database.getReference("ModuloStart").child("Livello").setValue("Principiante");
    // Firebase.database.getReference("ModuloStart").child("Professione").setValue("Studio");
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
                .padding(top = 22.dp, bottom = 0.dp, start = 22.dp, end = 22.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = null,
                modifier = Modifier.size(35.dp).clickable {
                    navController.navigate(Screens.ProfilePage.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            Box(modifier = Modifier.size(35.dp))

            Box(modifier = Modifier.size(35.dp))
        }
        Row {
            Text(
                text = "Modifica profilo",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
                //.verticalScroll(rememberScrollState())
        ){
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Nome:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = filledName,
                onValueChange = {
                    filledName = it
                },
                shape = RoundedCornerShape(15.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                placeholder =
                {
                    if (filledName == ""){
                        Text(text = "Nome utente",
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
                    containerColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    selectionColors = TextSelectionColors(MaterialTheme.colorScheme.onPrimary, MaterialTheme.colorScheme.tertiary),
                    textColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Mail:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = filledMail,
                onValueChange = {
                    filledMail = it
                },
                shape = RoundedCornerShape(15.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                placeholder =
                {
                    if(filledMail == ""){
                        Text(
                            text = "Email",
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
                    containerColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    selectionColors = TextSelectionColors(MaterialTheme.colorScheme.onPrimary, MaterialTheme.colorScheme.tertiary),
                    textColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            //Text("Data di nascita")
            /* val datePicker =
                 MaterialDatePicker.Builder.datePicker()
                     .setTitleText("SelezionaData")
                     .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                     .build()

             datePicker.isVisible*/

            //DATE PICKER DA IMPLEMENTARE

            //Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Categoria:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            filledCategory=categoryDropdownModify(vm)

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Lo fai per?",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            filledProfession=professionDropdownModify(vm)
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Livello di esperienza:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            filledLevel=levelDropdownModify(vm)

            Spacer(modifier = Modifier.height(22.dp))

            Button(
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .width(180.dp),
                onClick = {
                    if(filledName!=""){
                        Firebase.database.getReference("ModuloStart").child("Nome").setValue(filledName);
                        vm.setName(filledName)
                    }
                    if(filledMail.contains(regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.+[A-Za-z0-9.-]+\$".toRegex())){
                        Firebase.database.getReference("ModuloStart").child("Mail").setValue(filledMail);
                        vm.setMail(filledMail)
                        Firebase.database.getReference("ModuloStart").child("Categoria").setValue(filledCategory);
                        Firebase.database.getReference("ModuloStart").child("Livello").setValue(filledLevel);
                        Firebase.database.getReference("ModuloStart").child("Professione").setValue(filledProfession);
                        vm.setCategory(filledCategory)
                        vm.setLevel(filledLevel)
                        vm.setProfessione(filledProfession)

                        navController.navigate(Screens.ProfilePage.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        Firebase.database.getReference("UtenteGiaRegistrato").setValue(true)
                    }
                    else{
                        Toast.makeText(context, "Inserisci un campo email valido!", Toast.LENGTH_SHORT).show()
                    }

                })
            {
                Text(
                    text= "SALVA",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.background
                )
            }
            Spacer(modifier = Modifier.height(22.dp))
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun categoryDropdownModify(vm: MusaViewModel) : String{
    val context = LocalContext.current
    val category = arrayOf("Disegno", "Musica")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(vm.category.value.toString()) }

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded){
        painterResource(id = R.drawable.frecciasopra)
    } else painterResource(id = R.drawable.frecciasotto)

    Column(modifier = Modifier.fillMaxWidth()){
        OutlinedTextField(
            shape = RoundedCornerShape(15.dp),
            value = selectedText,
            onValueChange = {selectedText = it},
            readOnly = true,
            placeholder =
            { Text(text = vm.category.value.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
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
            for (index in category.indices) {
                val item = category[index]
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
                       // Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
                if (index < category.size - 1){
                    Divider(thickness = 3.dp, color = Color(0x1A001219))
                }

            }
        }
    }
    return selectedText
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun levelDropdownModify(vm: MusaViewModel) : String{
    val context = LocalContext.current
    val levels = arrayOf("Principiante", "Esperto")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(vm.level.value.toString()) }

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded){
        painterResource(id = R.drawable.frecciasopra)
    } else painterResource(id = R.drawable.frecciasotto)

    Column(modifier = Modifier.fillMaxWidth()){
        OutlinedTextField(
            shape = RoundedCornerShape(15.dp),
            value = selectedText,
            onValueChange = {selectedText = it},
            readOnly = true,
            placeholder =
            { Text(text = vm.level.value.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
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
            for (index in levels.indices) {
                val item = levels[index]
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
                       // Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
                if (index < levels.size - 1){
                    Divider(thickness = 3.dp, color = Color(0x1A001219))
                }

            }
        }
    }
    return selectedText
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun professionDropdownModify(vm: MusaViewModel) : String{
    val context = LocalContext.current
    val profession = arrayOf("Studio", "Professione", "Hobby")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(vm.professione.value.toString()) }

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded){
        painterResource(id = R.drawable.frecciasopra)
    } else painterResource(id = R.drawable.frecciasotto)

    Column(modifier = Modifier.fillMaxWidth()){
        OutlinedTextField(
            shape = RoundedCornerShape(15.dp),
            value = selectedText,
            onValueChange = {selectedText = it},
            readOnly = true,
            placeholder =
            { Text(text = vm.professione.value.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
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
            for (index in profession.indices) {
                val item = profession[index]
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
                       // Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
                if (index < profession.size - 1){
                    Divider(thickness = 3.dp, color = Color(0x1A001219))
                }

            }
        }
    }
    return selectedText
}

