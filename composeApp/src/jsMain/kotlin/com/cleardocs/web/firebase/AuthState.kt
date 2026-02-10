package com.cleardocs.web.firebase

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.await
import kotlin.js.asDynamic
import kotlin.js.json

object AuthState {
    private val auth = FirebaseClient.auth
    private var unsubscribe: (() -> Unit)? = null

    var currentUser by mutableStateOf<User?>(null)
        private set

    val isAuthenticated get() = currentUser != null

    val userDisplayName: String?
        get() = currentUser?.displayName ?: currentUser?.email

    fun init() {
        if (unsubscribe != null) return
        unsubscribe = onAuthStateChanged(auth) { user ->
            currentUser = user
        }
    }

    suspend fun signIn(email: String, password: String) {
        signInWithEmailAndPassword(auth, email, password).await()
    }

    suspend fun register(email: String, password: String) {
        createUserWithEmailAndPassword(auth, email, password).await()
    }

    suspend fun signInWithGoogle() {
        val provider = GoogleAuthProvider().setCustomParameters(json("prompt" to "select_account"))
        signInWithPopup(auth, provider).await()
    }

    suspend fun signOut() {
        signOut(auth).await()
    }

    fun translateError(throwable: Throwable): String {
        val code = (throwable.asDynamic().code as? String?)
        return when (code) {
            "auth/user-not-found" -> "Пользователь с таким email не найден"
            "auth/wrong-password" -> "Неверный пароль"
            "auth/email-already-in-use" -> "Email уже используется"
            "auth/weak-password" -> "Пароль слишком простой (минимум 6 символов)"
            "auth/invalid-email" -> "Неверный формат email"
            "auth/popup-closed-by-user" -> "Окно Google было закрыто пользователем"
            "auth/requires-recent-login" -> "Пожалуйста, авторизуйтесь заново"
            else -> throwable.message ?: "Неизвестная ошибка"
        }
    }
}
