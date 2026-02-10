package com.cleardocs.web.components.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.cleardocs.web.firebase.AuthState
import kotlin.js.Date
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun AppLayout(content: @Composable () -> Unit) {
    LaunchedEffect(Unit) {
        AuthState.init()
    }

    Div(attrs = { classes("app-shell") }) {
        Div(attrs = { classes("app-header") }) {
            Text("ClearDocs")
            Div(attrs = { classes("nav-links") }) {
                A(href = "/", attrs = { classes("nav-link") }) { Text("Главная") }
                A(href = "/login", attrs = { classes("nav-link") }) { Text("Войти") }
                A(href = "/sign-up", attrs = { classes("nav-link") }) { Text("Регистрация") }
            }
            Div(attrs = { classes("nav-links") }) {
                AuthState.userDisplayName?.let {
                    Text("Вы: $it")
                }
            }
        }
        Div(attrs = { classes("app-content") }) {
            content()
        }
        Div(attrs = { classes("app-footer") }) {
            Text("© ${Date().getFullYear()} ClearDocs Labs")
        }
    }
}
