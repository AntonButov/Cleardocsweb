package com.cleardocs.web.components.auth

import androidx.compose.runtime.*
import com.cleardocs.web.firebase.AuthState
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.events.MouseEvent

@Composable
fun SignUpForm() {
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var statusStyle by remember { mutableStateOf("status-error") }
    var isLoading by remember { mutableStateOf(false) }

    Div(attrs = { this["class"] = "auth-card" }) {
        H3 {
            Text("Создать новый профиль")
        }
        FieldLabel("Email")
        Input(InputType.Email) {
            this["class"] = "auth-input"
            value(email)
            onInput { email = it.value }
        }
        FieldLabel("Пароль")
        Input(InputType.Password) {
            this["class"] = "auth-input"
            value(password)
            onInput { password = it.value }
        }
        FieldLabel("Повторите пароль")
        Input(InputType.Password) {
            this["class"] = "auth-input"
            value(confirmPassword)
            onInput { confirmPassword = it.value }
        }
        Button(attrs = {
            this["class"] = "auth-button"
            if (isLoading) disabled()
            onClick { event: MouseEvent ->
                event.preventDefault()
                scope.launch {
                    if (password != confirmPassword) {
                        statusMessage = "Пароли не совпадают"
                        statusStyle = "status-error"
                        return@launch
                    }
                    isLoading = true
                    try {
                        AuthState.register(email.trim(), password)
                        statusMessage = "Регистрация прошла успешно"
                        statusStyle = "status-success"
                    } catch (error: Throwable) {
                        statusMessage = AuthState.translateError(error)
                        statusStyle = "status-error"
                    } finally {
                        isLoading = false
                    }
                }
            }
        }) {
            Text(if (isLoading) "Сохраняем..." else "Зарегистрироваться")
        }
        GoogleSignInButton(onResult = { message, success ->
            statusMessage = message
            statusStyle = if (success) "status-success" else "status-error"
        })
        statusMessage?.let {
            Div(attrs = { this["class"] = "status-badge $statusStyle" }) {
                Text(it)
            }
        }
        Div(attrs = { this["class"] = "auth-footer" }) {
            Text("Уже есть аккаунт? ")
            A(href = "/login", attrs = { this["class"] = "auth-footer" }) {
                Text("Войти")
            }
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Label(attrs = { this["class"] = "auth-footer" }) {
        Text(text)
    }
}
