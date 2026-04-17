package com.example.safepass2026.logic

import com.example.safepass2026.extensions.esMayorDeEdad
import com.example.safepass2026.extensions.esNombreValido
import com.example.safepass2026.extensions.normalizarTipoEntrada
import com.example.safepass2026.model.Asistente
import com.example.safepass2026.state.RegistroState

// Higher-order function: recibe una lambda como regla de prioridad
fun aplicarBeneficio(
    asistente: Asistente,
    regla: (Asistente) -> String
): String = regla(asistente)

fun procesarRegistro(
    nombreInput: String?,
    edadInput: String?,
    tipoEntradaInput: String?,
    reglaPrioridad: (Asistente) -> String = { asistente ->
        when (asistente.tipoEntrada.uppercase()) {
            "VIP" -> "Ingreso prioritario habilitado"
            "RESERVA" -> "Descuento de reserva aplicado"
            else -> "Ingreso general"
        }
    }
): RegistroState {

    val nombre = nombreInput?.trim().orEmpty()

    if (!nombre.esNombreValido()) {
        return RegistroState.Error("El nombre no puede estar vacío.", 400)
    }

    // toIntOrNull() con let: procesa la edad solo si no es nula ni inválida
    val edad = edadInput
        ?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?.toIntOrNull()
        ?.let { valor ->
            if (valor.esMayorDeEdad()) {
                valor
            } else {
                return RegistroState.Error("El asistente debe ser mayor de edad.", 403)
            }
        }
        ?: return RegistroState.Error("La edad es nula, vacía o inválida.", 400)

    val tipos = listOf("GENERAL", "VIP", "RESERVA")
    val tipo = tipoEntradaInput?.normalizarTipoEntrada() ?: "General"

    if (tipo.uppercase() !in tipos) {
        return RegistroState.Error("El tipo de entrada debe ser General, VIP o Reserva.", 400)
    }

    // apply: configura el objeto Asistente justo al crearlo
    val asistente = Asistente(
        nombre = nombre,
        edad = edad,
        tipoEntrada = tipo
    ).apply {
        require(tipoEntrada.isNotBlank())
    }

    val beneficio = aplicarBeneficio(asistente, reglaPrioridad)

    // run: opera sobre el objeto y devuelve el string de resumen
    val resumen = asistente.run {
        "Asistente: $nombre | Edad: ${edad ?: 0} | Entrada: $tipoEntrada | $beneficio"
    }

    return RegistroState.Success(asistente, resumen)
}