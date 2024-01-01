package it.polito.musaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.firebase.Firebase
import it.polito.musaapp.Backend.AppNavigation
import it.polito.musaapp.Backend.MusaViewModel

class MainActivity : ComponentActivity() {
    val vm by viewModels<MusaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           AppNavigation(vm, applicationContext)
        }
    }
}

