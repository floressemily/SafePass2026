package com.example.safepass2026.model

// Modelo inmutable del asistente. Usamos val para que los datos no cambien una vez creados.
data class Asistente(
    val nombre: String,
    val edad: Int?,
    val tipoEntrada: String
)