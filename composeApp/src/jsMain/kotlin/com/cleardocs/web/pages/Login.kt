package com.cleardocs.web.pages

import androidx.compose.runtime.Composable
import com.cleardocs.web.components.auth.LoginForm
import com.cleardocs.web.components.layout.AppLayout
import com.varabyte.kobweb.core.Page

@Page
@Composable
fun LoginPage() {
    AppLayout {
        LoginForm()
    }
}
