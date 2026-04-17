package com.example.safepass2026.extensions

/**
 * ESTUDIO - Extension Functions (William):
 * ¿Qué son? Permiten "inyectarle" o agregarle nuevas funciones a clases que ya existen
 * en Kotlin (como String o Int), sin tener que heredar ni modificar el código base origina de Kotlin.
 *
 * Ventaja: El código se lee idiomático y natural. Es mucho más legible hacer 'if (edad.esMayorDeEdad())'
 * que usar clases utilitarias clásicas de Java como 'if (ValidatorUtil.isMayor(edad))'.
 */

// Se le añade 'esNombreValido' a cualquier String. 'this' representa al String actual.
fun String.esNombreValido(): Boolean = this.trim().isNotEmpty()

// Se le añade 'esMayorDeEdad' a cualquier Int (número entero).
fun Int.esMayorDeEdad(): Boolean = this >= 18

fun String.normalizarTipoEntrada(): String =
    // Si la cadena tiene algo despues del trim, se queda con eso. Si queda vacío, toma su valor default gracias al Elvis (?:)
    this.trim().takeIf { it.isNotEmpty() } ?: "General"