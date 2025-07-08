package io.thomasgasangwa.bookcollection.presentation.view

import BookStoreTheme
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.thomasgasangwa.bookcollection.common.state.UserState
import io.thomasgasangwa.bookcollection.globalstate.UserStateHolder
import org.koin.android.ext.android.inject
import timber.log.Timber

val LocalUserData = compositionLocalOf<UserState> { UserState() }

class MainActivity : ComponentActivity() {
    private val userStateHolder: UserStateHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    "scaleX",
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    "scaleY",
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }
        enableEdgeToEdge()
        setContent {
            BookStoreTheme {
                val userState by userStateHolder.userState.collectAsState()
                Timber.e("user ${userState.user}")
                CompositionLocalProvider(
                    LocalUserData provides userState
                ) {
                    BookApp()
                }
            }
        }
    }
}