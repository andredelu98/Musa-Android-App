package it.polito.musaapp.Frontend

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Screens
import java.util.Vector


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
                SelettoreCountGiorni()
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text= "Quali giorni preferisci lavorare?",
                    modifier= Modifier.fillMaxWidth()
                )
                SelettoreGiorni()
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text= "Per quante settimane vuoi avere degli esercizi per la tua creatività?",
                    modifier= Modifier.fillMaxWidth()
                )
                SelettoreCountSettimane()
                Spacer(modifier = Modifier.height(8.dp))

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
}

@Composable
fun SelettoreCountGiorni(){
    var count by remember {
        mutableIntStateOf(0)
    }
   /* val myRef = Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni")
    myRef.get().addOnSuccessListener {
        Log.d("FORM", "valori ${it.value}");
        count=it.value.toString().toInt();
    }.addOnFailureListener {
        Log.d("FORM", "Error", it);
    }*/

    Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni").setValue("$count");
    Row(){

        Button(
            onClick = {
                if(count>0){
                    count--;
                }
            }
        ){
            Text("-")
        }

        Text("$count")

        Button(
            onClick = {
                if(count<7){
                    count++;
                }
            }
        ){
            Text("+")
        }
    }
}

@Composable
fun SelettoreCountSettimane(){
    var count by remember {
        mutableIntStateOf(0)
    }
    /* val myRef = Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni")
     myRef.get().addOnSuccessListener {
         Log.d("FORM", "valori ${it.value}");
         count=it.value.toString().toInt();
     }.addOnFailureListener {
         Log.d("FORM", "Error", it);
     }*/

    Firebase.database.getReference("ModuloEsercizi").child("NumeroSettimane").setValue("$count");
    Row(){

        Button(
            onClick = {
                if(count>0){
                    count--;
                }
            }
        ){
            Text("-")
        }

        Text("$count")

        Button(
            onClick = {
                count++;
            }
        ){
            Text("+")
        }
    }
}

@Composable
fun SelettoreGiorni(){
    val selected = remember {
        mutableStateListOf<Boolean>()
    }
    val days : Array<String> = arrayOf("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom")
    for(i in 0..6)
        selected.add(false)

    Row(
        modifier = Modifier.fillMaxWidth()

    ){
        for(i in 0..6){
            var isCardClicked by remember { mutableStateOf(false) }
            Card(
                modifier=Modifier
                    .clickable {
                        isCardClicked=!isCardClicked
                        if(isCardClicked){
                            Firebase.database.getReference("ModuloEsercizi")
                                .child("GiorniLiberi").child(days[i]).setValue(true);
                        }
                        else{
                            Firebase.database.getReference("ModuloEsercizi")
                                .child("GiorniLiberi").child(days[i]).setValue(false);
                        }
                    }
                    .background(if (isCardClicked) Color.Gray else Color.White)
                    .weight(1f),
            ){
                Text(days[i])
            }
        }
    }


}