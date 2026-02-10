package com.cleardocs.web.firebase

import kotlin.js.Json
import kotlin.js.json

external interface FirebaseConfigData {
    var apiKey: String?
    var authDomain: String?
    var projectId: String?
    var storageBucket: String?
    var messagingSenderId: String?
    var appId: String?
}

object FirebaseConfig {
    val config: FirebaseConfigData = json(
        "apiKey" to "YOUR_API_KEY",
        "authDomain" to "YOUR_AUTH_DOMAIN",
        "projectId" to "YOUR_PROJECT_ID",
        "storageBucket" to "YOUR_STORAGE_BUCKET",
        "messagingSenderId" to "YOUR_MESSAGING_SENDER_ID",
        "appId" to "YOUR_APP_ID"
    ) as FirebaseConfigData
}
