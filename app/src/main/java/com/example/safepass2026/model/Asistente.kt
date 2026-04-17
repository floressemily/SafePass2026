package com.example.safepass2026.model

/**
 * ESTUDIO (Flores/Mathias):
 * 1. 'data class': Genera automáticamente métodos útiles por detrás como toString(), equals(), hashCode() y copy().
 * 2. Inmutabilidad: Usamos 'val' (Value) en lugar de 'var' (Variable).
 *    Una vez instanciado el Asistente, sus propiedades no pueden cambiar, lo que previene "efectos secundarios" en la App.
 * 3. Null-Safety: 'Int?' con el signo de interrogación indica que la edad podría ser nula, indicando a Kotlin que debe blindarla.
 */
data class Asistente(
    val nombre: String,
    val edad: Int?,
    val tipoEntrada: String
)