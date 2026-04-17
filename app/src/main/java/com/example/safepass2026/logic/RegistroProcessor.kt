package com.example.safepass2026.logic

import com.example.safepass2026.extensions.esMayorDeEdad
import com.example.safepass2026.extensions.esNombreValido
import com.example.safepass2026.extensions.normalizarTipoEntrada
import com.example.safepass2026.model.Asistente
import com.example.safepass2026.state.RegistroState

/**
 * ESTUDIO - Higher-Order Function (Función de Orden Superior) (William):
 * Una función de orden superior es capaz de recibir "otra función" como parámetro.
 * Aquí recibe 'regla' de tipo (Asistente) -> String. Esto hace el código modular y extensible.
 */
fun aplicarBeneficio(
    asistente: Asistente,
    regla: (Asistente) -> String
): String = regla(asistente)

fun procesarRegistro(
    nombreInput: String?,
    edadInput: String?,
    tipoEntradaInput: String?,
    // Lambda por defecto: Inyecta esta función lambda si no se pasa otra regla.
    reglaPrioridad: (Asistente) -> String = { asistente ->
        when (asistente.tipoEntrada.uppercase()) {
            "VIP" -> "Ingreso prioritario habilitado"
            "RESERVA" -> "Descuento de reserva aplicado"
            else -> "Ingreso general"
        }
    }
): RegistroState {

    // ESTUDIO: ?.trim() es un "Safe Call". orEmpty() previene nulos y los vuelve "".
    val nombre = nombreInput?.trim().orEmpty()

    if (!nombre.esNombreValido()) {
        return RegistroState.Error("El nombre no puede estar vacío.")
    }

    /**
     * ESTUDIO - Scope Function 'let' (William):
     * Sirve para usar variables temporales si algo NO es nulo.
     * Si toIntOrNull() extrae un int correcto, el bloque 'let' arranca tomando ese 'valor'.
     * Si alguna conversión falla = es Null = se salta hacia el operador Elvis (?:) del final.
     */
    val edad = edadInput
        ?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?.toIntOrNull()
        ?.let { valor ->
            // Se usa la función de extensión 'esMayorDeEdad()' que hizo William
            if (valor.esMayorDeEdad()) {
                valor
            } else {
                return RegistroState.Error("El asistente debe ser mayor de edad.")
            }
        }
        // ESTUDIO: Operador Elvis (?:). Si todo lo de arriba falló/es nulo, retornar este Error.
        ?: return RegistroState.Error("La edad es nula, vacía o inválida.")

    /**
     * ESTUDIO - Scope Function 'apply' (William):
     * Acompaña a un objeto recién nacido para "configurarlo".
     * Nace Asistente e INMEDIATAMENTE se aplica una regla obligatoria (require).
     */
    val asistente = Asistente(
        nombre = nombre,
        edad = edad,
        tipoEntrada = tipoEntradaInput?.normalizarTipoEntrada() ?: "General"
    ).apply {
        require(tipoEntrada.isNotBlank())
    }

    val beneficio = aplicarBeneficio(asistente, reglaPrioridad)

    /**
     * ESTUDIO - Scope Function 'run' (William):
     * A diferencia de apply, 'run' DEVUELVE la última línea calculada.
     * Aquí opera sobre 'asistente', toma sus datos e interpola un String (Plantillas $).
     */
    val resumen = asistente.run {
        "Asistente: $nombre | Edad: ${edad ?: 0} | Entrada: $tipoEntrada | $beneficio"
    }

    return RegistroState.Success(asistente, resumen)
}