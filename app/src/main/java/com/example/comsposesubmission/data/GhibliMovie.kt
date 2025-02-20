package com.example.comsposesubmission.data



data class GhibliMovie (
    val id: String,
    val title: String,
    val description: String,
    val director: String,
    val producer: String,
    val releaseDate: String,
    val photoUrl: String,
    val trailerUrl: String,
    val isFavorite : Boolean = false
)