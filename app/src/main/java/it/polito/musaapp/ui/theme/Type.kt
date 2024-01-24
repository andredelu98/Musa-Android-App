package it.polito.musaapp.ui.theme

import androidx.compose.material3.Typography
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
    ),
    titleLarge = TextStyle( //TASTO AIUTO!
        fontFamily = bangers,
        fontWeight = FontWeight.Normal,
        fontSize = 90.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 35.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = balsamiqSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
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