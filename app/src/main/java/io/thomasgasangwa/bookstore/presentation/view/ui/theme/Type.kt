package io.thomasgasangwa.bookstore.presentation.view.ui.theme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.thomasgasangwa.bookstore.R


val Barlow = FontFamily(
    Font(R.font.barlow_regular),
    Font(R.font.barlow_extrabold),
)

val Poppins = FontFamily(
    Font(R.font.poppins_extralight),
    Font(R.font.poppins_light),
)



val Typography = Typography(

    titleLarge = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 12.sp
    )
)

