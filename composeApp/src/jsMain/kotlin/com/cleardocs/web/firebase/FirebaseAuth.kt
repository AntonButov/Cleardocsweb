package com.cleardocs.web.firebase

import kotlin.js.Json
import kotlin.js.Promise

@JsModule("firebase/app")
@JsNonModule
external fun initializeApp(config: FirebaseConfigData): FirebaseApp

@JsModule("firebase/auth")
@JsNonModule
external fun getAuth(app: FirebaseApp = definedExternally): Auth

@JsModule("firebase/auth")
@JsNonModule
external fun signInWithEmailAndPassword(auth: Auth, email: String, password: String): Promise<UserCredential>

@JsModule("firebase/auth")
@JsNonModule
external fun createUserWithEmailAndPassword(auth: Auth, email: String, password: String): Promise<UserCredential>

@JsModule("firebase/auth")
@JsNonModule
external fun signInWithPopup(auth: Auth, provider: GoogleAuthProvider): Promise<UserCredential>

@JsModule("firebase/auth")
@JsNonModule
external fun signOut(auth: Auth): Promise<Unit>

@JsModule("firebase/auth")
@JsNonModule
external fun onAuthStateChanged(auth: Auth, callback: (User?) -> Unit): () -> Unit

external interface FirebaseApp
external interface Auth

external interface User {
    val uid: String?
    val email: String?
    val displayName: String?
}

external interface UserCredential {
    val user: User
}

external interface FirebaseError {
    val code: String?
}

@JsModule("firebase/auth")
@JsNonModule
external class GoogleAuthProvider {
    fun setCustomParameters(parameters: Json = definedExternally): GoogleAuthProvider
}

object FirebaseClient {
    val app: FirebaseApp = initializeApp(FirebaseConfig.config)
    val auth: Auth = getAuth(app)
}
