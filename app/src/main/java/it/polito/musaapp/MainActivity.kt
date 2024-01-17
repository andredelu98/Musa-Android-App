package it.polito.musaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.AppNavigation
import it.polito.musaapp.Backend.MusaViewModel

class MainActivity : ComponentActivity() {
    val vm by viewModels<MusaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           AppNavigation(vm, applicationContext)
        }

        //RESET IMPOSTAZIONI FIREBASE
        /*
        Firebase.database.getReference("UtenteGiaRegistrato").setValue(false)
        Firebase.database.getReference("ModuloStart").child("Nome").setValue("")
        Firebase.database.getReference("ModuloStart").child("Mail").setValue("")
        Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni").setValue(0)
        Firebase.database.getReference("ModuloEsercizi").child("NumeroSettimane").setValue(0)
        val days: Array<String> = arrayOf("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom")
        for (i in 0..6){
            Firebase.database.getReference("ModuloEsercizi")
             .child("GiorniLiberi").child(days[i]).setValue(false);
        }*/
    }
}

