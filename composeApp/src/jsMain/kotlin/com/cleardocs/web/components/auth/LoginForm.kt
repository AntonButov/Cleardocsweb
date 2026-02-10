package com.cleardocs.web.components.auth

import androidx.compose.runtime.*
import com.cleardocs.web.firebase.AuthState
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text

@Composable
fun LoginForm() {
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var serverMessage by remember { mutableStateOf<String?>(null) }
    var statusStyle by remember { mutableStateOf("status-error") }
    var isLoading by remember { mutableStateOf(false) }

    Div(attrs = { classes("auth-card") }) {
        H3 {
            Text("Добро пожаловать обратно")
        }
        FieldLabel("Email")
        Input(InputType.Email) {
            classes("auth-input")
            value(email)
            onInput { email = it.value }
        }
        FieldLabel("Пароль")
        Input(InputType.Password) {
            classes("auth-input")
            value(password)
            onInput { password = it.value }
        }
        Button(attrs = {
            classes("auth-button")
            if (isLoading) disabled()
            onClick { event ->
                event.preventDefault()
                scope.launch {
                    isLoading = true
                    try {
                        AuthState.signIn(email.trim(), password)
                        serverMessage = "Вы успешно вошли"
                        statusStyle = "status-success"
                    } catch (error: Throwable) {
                        serverMessage = AuthState.translateError(error)
                        statusStyle = "status-error"
                    } finally {
                        isLoading = false
                    }
                }
            }
        }) {
            Text(if (isLoading) "Выполняется..." else "Войти")
        }
        GoogleSignInButton(onResult = { message, success ->
            serverMessage = message
            statusStyle = if (success) "status-success" else "status-error"
        })
        serverMessage?.let {
            Div(attrs = { classes("status-badge", statusStyle) }) {
                Text(it)
            }
        }
        Div(attrs = { classes("auth-footer") }) {
            Text("Еще нет аккаунта? ")
            PageLink(to = "/sign-up", text = "Зарегистрироваться")
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Label(attrs = { classes("auth-footer") }) {
        Text(text)
    }
}

@Composable
private fun PageLink(to: String, text: String) {
    A(href = to, attrs = { classes("auth-footer") }) {
        Text(text)
    }
}
