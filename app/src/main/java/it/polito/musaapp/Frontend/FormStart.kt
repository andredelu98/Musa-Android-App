package it.polito.musaapp.Frontend

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.R
import it.polito.musaapp.Screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormStart(navController: NavController, vm: MusaViewModel){
    var filledName by remember {
        mutableStateOf("")
    }
    var filledMail by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
   // Firebase.database.getReference("ModuloStart").child("Categoria").setValue("Disegno");
   // Firebase.database.getReference("ModuloStart").child("Livello").setValue("Principiante");
   // Firebase.database.getReference("ModuloStart").child("Professione").setValue("Studio");
    Firebase.database.getReference("ModuloStart").child("Nome").setValue(filledName);
    Firebase.database.getReference("ModuloStart").child("Mail").setValue(filledMail);
    vm.setName(filledName)
    vm.setMail(filledMail)

    Box(modifier=Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.big_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp, vertical = 25.dp)
                .verticalScroll(rememberScrollState())
        ){
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                text = "MUSA",
                style = MaterialTheme.typography.displayLarge,
            )
            Spacer(modifier = Modifier.height(25.dp))
            OutlinedTextField(
                value = filledName,
                onValueChange = {
                    filledName = it
                },
                shape = RoundedCornerShape(15.dp),
                placeholder =
                { Text(text = "Nome utente",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF775c15)
                )
                },
                modifier = Modifier
                    .border(5.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(15.dp))
                    .fillMaxWidth(),

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    textColor = MaterialTheme.colorScheme.onPrimary
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
            //Text(text = vm.mail.value.toString())
            OutlinedTextField(
                value = filledMail,
                onValueChange = {
                    filledMail = it
                },
                shape = RoundedCornerShape(15.dp),
                placeholder =
                { Text(text = "Email",
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

            CategoryDropdown(vm)

            Spacer(modifier = Modifier.height(20.dp))

            ProfessionDropdown(vm)

            Spacer(modifier = Modifier.height(20.dp))

            LevelDropdown(vm)

            Spacer(modifier = Modifier.height(45.dp))

            Button(
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .width(160.dp),
                onClick = {
                    if(filledName==""||
                        filledMail==""||
                        vm.category.value==""||
                        vm.level.value==""
                    ){
                        Toast.makeText(context, "Inserisci tutti i dati per continuare", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        navController.navigate(Screens.HelpPage.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        Firebase.database.getReference("UtenteGiaRegistrato").setValue(true)
                        vm.setRegistered(true)
                    }
                })
            {
                Text(
                    text= "AVANTI",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.background
                )
            }

        }
    }
    

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(vm: MusaViewModel){
    val context = LocalContext.current
    val category = arrayOf("Disegno", "Musica")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

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
            { Text(text = "Ambito creativo",
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
                        Firebase.database.getReference("ModuloStart").child("Categoria").setValue(selectedText);
                        vm.setCategory(selectedText)
                        expanded = false
                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
                if (index < category.size - 1){
                    Divider(thickness = 3.dp, color = Color(0x1A001219))
                }

            }
        }
    }
}



/*fun getTextFieldShape(expanded: Boolean): Shape {
    return if (expanded) {
        RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)
    } else {
        RoundedCornerShape(15.dp)
    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionDropdown(vm: MusaViewModel){
    val context = LocalContext.current
    val profession = arrayOf("Studio", "Professione", "Hobby")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

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
            { Text(text = "Lo fai per",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF775c15)
            ) },
            trailingIcon = {
                Icon(icon , "", tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .size(16.dp))
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
                        Firebase.database.getReference("ModuloStart").child("Professione").setValue(selectedText);
                        vm.setProfessione(selectedText)
                        expanded = false
                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
                if (index < profession.size - 1){
                    Divider(thickness = 3.dp, color = Color(0x1A001219))
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelDropdown(vm: MusaViewModel){
    val context = LocalContext.current
    val levels = arrayOf("Principiante", "Esperto")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

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
            { Text(text = "Livello di esperienza",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF775c15)
            ) },
            trailingIcon = {
                Icon(icon , "", tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .size(16.dp))
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
                        Firebase.database.getReference("ModuloStart").child("Livello").setValue(selectedText);
                        vm.setLevel(selectedText)
                        expanded = false
                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
                if (index < levels.size - 1){
                    Divider(thickness = 3.dp, color = Color(0x1A001219))
                }

            }
        }
    }
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelDropdown(vm: MusaViewModel){
    val context = LocalContext.current
    val levels = arrayOf("Principiante", "Esperto")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                levels.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item) },
                        onClick = {
                            selectedText = item
                            Firebase.database.getReference("ModuloStart").child("Livello").setValue(
                                selectedText
                            );
                            vm.setLevel(selectedText)
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}*/

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionDropdown(vm:MusaViewModel){
    val context = LocalContext.current
    val profession = arrayOf("Studio", "Professione", "Hobby")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                profession.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            Firebase.database.getReference("ModuloStart").child("Professione").setValue(
                                selectedText
                            );
                            vm.setProfessione(selectedText)
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}*/
