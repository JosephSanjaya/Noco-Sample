package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.ViewPumpAppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.edit
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.b3nedikt.restring.Restring
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val appCompatDelegate: AppCompatDelegate by lazy {
        ViewPumpAppCompatDelegate(
            baseDelegate = super.getDelegate(),
            baseContext = this,
            wrapContext = Restring::wrapContext
        )
    }

    override fun getDelegate(): AppCompatDelegate {
        return appCompatDelegate
    }
    private val viewModel by viewModels<MainActivityViewModel>()
    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val state by viewModel.state.collectAsState()
                LaunchedEffect(Unit) {
                    viewModel.fetchStrings()
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConstraintLayout {
                        val (loadingRef, textRef) = createRefs()
                        AnimatedVisibility(modifier = Modifier.constrainAs(loadingRef) {
                            this.centerHorizontallyTo(parent)
                            this.centerVerticallyTo(parent)
                        }, visible = state.isLoading) {
                            CircularProgressIndicator()
                        }

                        AnimatedVisibility(modifier = Modifier.constrainAs(textRef) {
                            this.centerHorizontallyTo(parent)
                            this.centerVerticallyTo(parent)
                        }, visible = !state.isLoading) {
                            Greeting(
                                if (state.isError) state.throwable?.message.toString() else stringResource(
                                    id = R.string.label_hello_world
                                )
                            )
                        }
                        val rowRef = createRef()
                        AnimatedVisibility(modifier = Modifier.constrainAs(rowRef) {
                            centerHorizontallyTo(textRef)
                            top.linkTo(textRef.bottom)
                        }, visible = !state.isLoading) {
                            Row(modifier = Modifier.padding(16.dp)) {
                                Button(onClick = { Locale("id", "ID").changeLocale() }) {
                                    Text(text = "ID")
                                }
                                Button(onClick = { Locale.US.changeLocale() }) {
                                    Text(text = "EN")
                                }
                                Button(onClick = { Locale("th", "TH").changeLocale() }) {
                                    Text(text = "TH")
                                }
                                Button(onClick = { Locale("vi", "VI").changeLocale() }) {
                                    Text(text = "VI")
                                }
                                Button(onClick = { Locale("my", "My").changeLocale() }) {
                                    Text(text = "MY")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun Locale.changeLocale() {
        Restring.locale = this
        preferences.edit { putString("locale", this@changeLocale.toLanguageTag()) }
        Intent(this@MainActivity, MainActivity::class.java).run {
            startActivity(this)
        }
        finish()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}