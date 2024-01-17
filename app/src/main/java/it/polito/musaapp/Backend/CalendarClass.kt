package it.polito.musaapp.Backend/*

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Events
import com.google.api.services.calendar.model.Events.*

class CalendarClass : ComponentActivity() {

    private val calendarScope = CalendarScopes.CALENDAR
    private lateinit var credential: GoogleAccountCredential

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        credential = GoogleAccountCredential.usingOAuth2(this, setOf(calendarScope))
        credential.selectedAccountName = "francesca.porro11@gmail.com" // Inserisci il tuo account Gmail

        setContent {
            MyApp(credential)
        }
    }
}

@Composable
fun MyApp(credential: GoogleAccountCredential) {
    val events = remember { getCalendarEvents(credential) }

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        EventList(events)
    }
}

@Composable
fun EventList(events: List<Event>) {
    LazyColumn {
        items(events) { event ->
            // Visualizza gli eventi nel tuo layout Jetpack Compose
            // Puoi personalizzare questa parte in base alle tue esigenze
            // Ad esempio, puoi visualizzare il titolo e la data dell'evento
        }
    }
}

fun getCalendarEvents(credential: GoogleAccountCredential): List<Event> {
    // Esegui la chiamata all'API Google Calendar per recuperare gli eventi
    val service = Calendar.Builder(
        GoogleNetHttpTransport.newTrustedTransport(),
        JacksonFactory.getDefaultInstance(),
        credential
    )
        .setApplicationName("Musa")
        .build()

    val events: Events = service.events().list("primary").execute()

    return events.items ?: emptyList()
}
*/

