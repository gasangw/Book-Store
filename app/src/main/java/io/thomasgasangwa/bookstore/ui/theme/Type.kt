import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.thomasgasangwa.bookstore.R


val Barlow = FontFamily(
    Font(R.font.Barlow_Regular),
    Font(R.font.Barlow_ExtraBold),
)

val Poppin = FontFamily(
    Font(R.font.Poppins_ExtraLight),
    Font(R.font.Poppins_Light),
)



val Typography = Typography(
   displayLarge = TextStyle(
       fontFamily = Barlow,
       fontWeight = FontWeight.ExtraBold,
       fontSize = 36.sp
   ),
    displayMedium = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Poppin,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Poppin,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 12.sp
    )
)

