package it.polito.musaapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import it.polito.musaapp.Backend.AppNavigation
import it.polito.musaapp.Backend.GetProjectsFromDb
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.ui.theme.MusaAppTheme

class MainActivity : ComponentActivity() {
    val vm by viewModels<MusaViewModel>()
    private var isImmersiveModeEnabled = false

    private val prefs: SharedPreferences by lazy {
        getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFirstRun = prefs.getBoolean("isFirstRun", true)
        if (isFirstRun) {
            vm.setTutorial(true)
            prefs.edit().putBoolean("isFirstRun", false).apply()
        }

        setContent {
            MusaAppTheme {
                GetProjectsFromDb(vm = vm)
                AppNavigation(vm, applicationContext)
            }
        }

        // Attiva l'Immersive Mode
        enableImmersiveMode()

        val rootView: View = findViewById(android.R.id.content)

        rootView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                rootView.viewTreeObserver.removeOnPreDrawListener(this)

                // Aggiungi il gesto di swipe verso l'alto
                rootView.setOnTouchListener { view, event ->
                    when (event.action) {
                        MotionEvent.ACTION_UP -> {
                            toggleImmersiveMode()
                            view.performClick() // Chiamare performClick() per simulare il click
                            true
                        }
                        else -> false
                    }
                }
                return true
            }
        })
    }

    private fun enableImmersiveMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.navigationBars()) // Nascondi solo la navigation bar
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        isImmersiveModeEnabled = true
    }

    private fun disableImmersiveMode() {
        WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.navigationBars()) // Mostra solo la navigation bar
        isImmersiveModeEnabled = false
    }

    private fun toggleImmersiveMode() {
        if (isImmersiveModeEnabled) {
            disableImmersiveMode()
        } else {
            enableImmersiveMode()
        }
    }
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