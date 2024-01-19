package it.polito.musaapp.Backend

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import it.polito.musaapp.R
import res.layout.*

class CalendarActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        // Opzionale: gestire il client WebView per personalizzare il comportamento
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                // Gestire il caricamento delle risorse, se necessario
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        // Opzionale: gestire il client Chrome WebView per mostrare il progresso di caricamento
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                // Gestire il progresso di caricamento, se necessario
            }
        }

        // Carica il calendario FullCalendar nel WebView
        val html = """
            <!DOCTYPE html>
            <html>
            <head>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.css" />
                <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.js"></script>
            </head>
            <body>
                <div id="calendar"></div>
                <script>
                    $(document).ready(function() {
                        $('#calendar').fullCalendar({
                            // Configura le opzioni del calendario
                        });
                    });
                </script>
            </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
    }
}