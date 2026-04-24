package co.edu.unab.carlossaulvillabona.exploraapp

import android.util.Patterns

//Restornar un true si es valido y false si no lo es
//tambien retorne una cadena que me diga que paso si no es valido


fun validateEmail(email: String) : Pair<Boolean, String> {
    return when {
        email.isEmpty() -> Pair(false, "El correo electrónico no puede estar vacío")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair(false, "El correo electrónico no es válido")
        !email.endsWith("@gmail.com") -> Pair(false, "Ese email no es coorporativo")
        else -> Pair(true, "")
    }

}

fun validatePassword(password: String) : Pair<Boolean, String> {
    return when {
        password.isEmpty() -> Pair(false, "La contraseña no puede estar vacía")
        password.length < 8 -> Pair(false, "La contraseña debe tener al menos 6 caracteres")
        !password.any { it.isDigit() } -> Pair(false, "La contraseña debe contener al menos un número")
        else -> Pair(true, "")
    }
}
