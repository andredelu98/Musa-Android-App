package it.polito.musaapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import it.polito.musaapp.R

val bangers = FontFamily(
    Font(R.font.bangers_regular, FontWeight.Normal),
)
val balsamiqSans = FontFamily(
    Font(R.font.balsamiqsans_bold, FontWeight.Bold),
    Font(R.font.balsamiqsans_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.balsamiqsans_regular, FontWeight.Normal),
    Font(R.font.balsamiqsans_italic, FontWeight.Normal, FontStyle.Italic),
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 60.sp,
        color = Color(0xFF001E2A)
    ),
    titleLarge = TextStyle( //TASTO AIUTO!
        fontFamily = bangers,
        fontWeight = FontWeight.Normal,
        fontSize = 90.sp,
        color = Color(0xFF001E2A)
    ),
    headlineLarge = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        color = Color(0xFF001E2A)
    ),
    headlineMedium = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        color = Color(0xFF001E2A)
    ),
    headlineSmall = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color(0xFF001E2A)
    ),
    bodyLarge = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        color = Color(0xFF001E2A)
    ),
    bodyMedium = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = Color(0xFF001E2A)
    ),
    bodySmall = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = Color(0xFF001E2A)
    ),
    labelMedium = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        color = Color(0xFF001E2A)
    ),
    labelSmall = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Color(0xFFFAEABC)
    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)