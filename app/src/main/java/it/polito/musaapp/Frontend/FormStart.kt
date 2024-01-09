package it.polito.musaapp.Frontend

import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Screens
import kotlinx.coroutines.selects.select
import java.util.Locale.Category


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormStart(navController: NavController, vm: MusaViewModel){
    var filledName by remember {
        mutableStateOf("")
    }
    var filledMail by remember {
        mutableStateOf("")
    }

   // Firebase.database.getReference("ModuloStart").child("Categoria").setValue("Disegno");
   // Firebase.database.getReference("ModuloStart").child("Livello").setValue("Principiante");
   // Firebase.database.getReference("ModuloStart").child("Professione").setValue("Studio");
    Firebase.database.getReference("ModuloStart").child("Nome").setValue(filledName);
    Firebase.database.getReference("ModuloStart").child("Mail").setValue(filledMail);
    vm.setName(filledName)
    vm.setMail(filledMail)

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text("Nome")
        OutlinedTextField(
            value = filledName,
            onValueChange = {
                filledName = it
            },
            placeholder = { Text(text = "Inserisci il nome")},
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp)) // Make the corners rounded with a radius of 8dp
                .background(Color.Gray)
                .size(width = 500.dp, height = 50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0x00FFFFFF),
                unfocusedBorderColor = Color(0x00FFFFFF)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Mail")
        OutlinedTextField(
            value = filledMail,
            onValueChange = {
                filledMail = it
            },
            placeholder = { Text(text = "Inserisci la mail")},
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp)) // Make the corners rounded with a radius of 8dp
                .background(Color.Gray)
                .size(width = 500.dp, height = 50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0x00FFFFFF),
                unfocusedBorderColor = Color(0x00FFFFFF)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))


        Spacer(modifier = Modifier.height(8.dp))
        Text("Categoria")
        CategoryDropdown(vm)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Perchè lo fai?")
        ProfessionDropdown(vm)
        Spacer(modifier = Modifier.height(8.dp))

        Text("A che livello artistico ti senti?")
        LevelDropdown(vm)
        Spacer(modifier = Modifier.height(8.dp))



        Button(onClick = {
            navController.navigate(Screens.HelpPage.name) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            Firebase.database.getReference("UtenteGiaRegistrato").setValue(true)
        })
        {
            Text("INVIA")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(vm:MusaViewModel){
    val context = LocalContext.current
    val category = arrayOf("Disegno", "Musica")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(category[0]) }

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
                category.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            Firebase.database.getReference("ModuloStart").child("Categoria").setValue(selectedText);
                            vm.setCategory(selectedText)
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelDropdown(vm: MusaViewModel){
    val context = LocalContext.current
    val levels = arrayOf("Principiante", "Intermedio", "Esperto")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(levels[0]) }

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
                        text = { Text(text = item) },
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionDropdown(vm:MusaViewModel){
    val context = LocalContext.current
    val profession = arrayOf("Studio", "Professione", "Hobby")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(profession[0]) }

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
}
