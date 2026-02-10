package com.cleardocs.web.components.auth

import androidx.compose.runtime.*
import com.cleardocs.web.firebase.AuthState
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.events.MouseEvent

@Composable
fun GoogleSignInButton(onResult: (String, Boolean) -> Unit) {
    val scope = rememberCoroutineScope()
    var processing by remember { mutableStateOf(false) }

    Button(attrs = {
        this["class"] = "auth-button"
        if (processing) {
            disabled()
        }
        onClick { event: MouseEvent ->
            event.preventDefault()
            scope.launch {
                processing = true
                try {
                    AuthState.signInWithGoogle()
                    onResult("Google успешно авторизован", true)
                } catch (error: Throwable) {
                    onResult(AuthState.translateError(error), false)
                } finally {
                    processing = false
                }
            }
        }
    }) {
        Span {
            Text(if (processing) "Соединение..." else "Войти через Google")
        }
    }
}
