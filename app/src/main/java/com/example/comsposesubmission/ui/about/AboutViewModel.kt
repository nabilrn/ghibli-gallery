package com.example.comsposesubmission.ui.about

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AboutViewModel : ViewModel() {
    private val _profileName = MutableStateFlow("Nabil Rizki Navisa")
    val profileName: StateFlow<String> = _profileName.asStateFlow()

    private val _profileTitle = MutableStateFlow("Android Developer")
    val profileTitle: StateFlow<String> = _profileTitle.asStateFlow()

    private val _emailAddress = MutableStateFlow("mailto:nabilrizkinavisa@gmail.com")
    val emailAddress: StateFlow<String> = _emailAddress.asStateFlow()

    private val _githubUrl = MutableStateFlow("https://github.com/nabilrn")
    val githubUrl: StateFlow<String> = _githubUrl.asStateFlow()

    private val _linkedInUrl = MutableStateFlow("https://linkedin.com/in/nabilrizkinavisa2004")
    val linkedInUrl: StateFlow<String> = _linkedInUrl.asStateFlow()
}