package com.cleardocs.web.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.cleardocs.web.components.layout.AppLayout
import com.cleardocs.web.firebase.AuthState
import com.varabyte.kobweb.core.Page
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun IndexPage() {
    val scope = rememberCoroutineScope()

    AppLayout {
        Div(attrs = { classes("home-card") }) {
            if (AuthState.isAuthenticated) {
                H1 {
                    Text("Привет, ${AuthState.userDisplayName ?: "пользователь"}")
                }
                P {
                    Text("Спасибо, что пользуетесь ClearDocs. Вы вошли в систему.")
                }
                Button(attrs = {
                    classes("auth-button")
                    onClick {
                        scope.launch {
                            AuthState.signOut()
                        }
                    }
                }) {
                    Text("Выйти")
                }
            } else {
                H1 {
                    Text("Добро пожаловать в ClearDocs")
                }
                P {
                    Text("Здесь вы можете хранить и получать доступ к важным документам, защищённым Firebase.")
                }
            }
        }
    }
}
