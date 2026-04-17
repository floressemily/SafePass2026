package com.example.safepass2026.extensions

// Extension functions para validar datos del asistente sin clases utilitarias externas

fun String.esNombreValido(): Boolean = this.trim().isNotEmpty() && this.none { it.isDigit() }

fun Int.esMayorDeEdad(): Boolean = this >= 18

fun String.normalizarTipoEntrada(): String = this.trim().takeIf { it.isNotEmpty() } ?: "General"