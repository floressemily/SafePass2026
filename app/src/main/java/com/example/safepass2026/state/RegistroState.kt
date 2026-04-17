package com.example.safepass2026.state

import com.example.safepass2026.model.Asistente

/**
 * ESTUDIO (Flores):
 * 'sealed class' (Clase Sellada): Es una versión mucho más poderosa que un 'Enum'.
 * Limita la herencia estrictamente a las clases declaradas en este archivo.
 * El compilador sabe de antemano exactamente cuántos estados son (Idle, Success, Error).
 * Esto permite que Gus en la UI pueda usar un 'when' EXHAUSTIVO sin necesitar escribir un 'else'.
 */
sealed class RegistroState {
    
    // object: Un objeto único (Singleton) porque el estado Idle no necesita guardar datos.
    object Idle : RegistroState()

    // data class: A diferencia de un enum, este sub-estado SÍ puede almacenar datos útiles.
    data class Success(
        val asistente: Asistente,
        val resumen: String
    ) : RegistroState()

    // Encapsula y protege el mensaje del error que disparó la lógica.
    data class Error(val mensaje: String) : RegistroState()
}