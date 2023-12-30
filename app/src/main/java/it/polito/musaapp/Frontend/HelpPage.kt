package it.polito.musaapp.Frontend

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.polito.musaapp.Backend.MusaViewModel


@Composable
fun HelpPage(
    navController: NavController,
    musaViewModel: MusaViewModel,
    applicationContext: Context
){
    //Text("HelpPage")

    PageContent(musaViewModel)
    PopUp(musaViewModel, applicationContext)

    Log.d("POPUP", "${musaViewModel.popUpHelp.value}")
    musaViewModel.unsetPopUpHelp()
}

@Composable
fun PageContent(musaViewModel: MusaViewModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        Button(
            onClick = {
                //POPUP
                Log.d("POPUP", "${musaViewModel.popUpHelp.value}")
                musaViewModel.setPopUpHelp()
                Log.d("POPUP", "${musaViewModel.popUpHelp.value}")
            }
        ){
            Text(
                text= "Aiuto"
                //AGGIUNGERE SOTTOLINEATURA
            )
        }

        Text(
            text="Oppure inserisci un tuo progetto personale",
            modifier = Modifier.clickable { /*NAVIGARE VERSO PROGETTI*/},
        )
    }
}

@Composable
fun PopUp(vm: MusaViewModel, applicationContext: Context){

   // val value by vm.popUpHelp.
    //if(value == true)
   // {

        Box(
            modifier= Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .background(
                    Color.Gray
                )
        ){
            Column(
                modifier= Modifier.fillMaxSize()
            ){
                Text(
                    text= "Quanti giorni a settimana vuoi lavorare?",
                    modifier= Modifier.fillMaxWidth()
                )
                //SELETTORE COUNT
                Text(
                    text= "Quali giorni preferisci lavorare?",
                    modifier= Modifier.fillMaxWidth()
                )
                //SELETTORE GIORNI
                Text(
                    text= "Per quante settimane vuoi avere degli esercizi per la tua creatività?",
                    modifier= Modifier.fillMaxWidth()
                )
                //SELETTORE COUNT
                Button(
                    onClick = {
                      vm.unsetPopUpHelp()
                        Log.d("POPUP", "${vm.popUpHelp.value}")
                    }
                ){
                    Text("Avvia")
                }
            }
        }
   // }
}