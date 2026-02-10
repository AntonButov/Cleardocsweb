package com.cleardocs.web.components.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.cleardocs.web.firebase.AuthState
import kotlin.js.Date
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun AppLayout(content: @Composable () -> Unit) {
    LaunchedEffect(Unit) {
        AuthState.init()
    }

    Div(attrs = { this["class"] = "app-shell" }) {
        Div(attrs = { this["class"] = "app-header" }) {
            Text("ClearDocs")
            Div(attrs = { this["class"] = "nav-links" }) {
                A(href = "/", attrs = { this["class"] = "nav-link" }) { Text("Главная") }
                A(href = "/login", attrs = { this["class"] = "nav-link" }) { Text("Войти") }
                A(href = "/sign-up", attrs = { this["class"] = "nav-link" }) { Text("Регистрация") }
            }
            Div(attrs = { this["class"] = "nav-links" }) {
                AuthState.userDisplayName?.let {
                    Text("Вы: $it")
                }
            }
        }
        Div(attrs = { this["class"] = "app-content" }) {
            content()
        }
        Div(attrs = { this["class"] = "app-footer" }) {
            Text("© ${Date().getFullYear()} ClearDocs Labs")
        }
    }
}
