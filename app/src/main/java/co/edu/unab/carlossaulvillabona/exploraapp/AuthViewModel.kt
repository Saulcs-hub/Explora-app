package co.edu.unab.carlossaulvillabona.exploraapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val isUserLoggedIn: Boolean
        get() = auth.currentUser != null

    // LOGIN
    fun login(email: String, password: String) {
        // Validaciones previas
        val error = validateLoginFields(email, password)
        if (error != null) {
            _uiState.update { it.copy(errorMessage = error) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: FirebaseAuthInvalidUserException) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "No existe una cuenta con este correo.")
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Correo o contraseña incorrectos.")
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Error inesperado: ${e.message}")
                }
            }
        }
    }

    // REGISTRO
    fun register(name: String, email: String, password: String, confirmPassword: String) {
        val error = validateRegisterFields(name, email, password, confirmPassword)
        if (error != null) {
            _uiState.update { it.copy(errorMessage = error) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                // Guardar el nombre en el perfil
                val profileUpdates = com.google.firebase.auth.userProfileChangeRequest {
                    displayName = name
                }
                result.user?.updateProfile(profileUpdates)?.await()
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: FirebaseAuthWeakPasswordException) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "La contraseña debe tener al menos 6 caracteres.")
                }
            } catch (e: FirebaseAuthUserCollisionException) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Ya existe una cuenta con este correo.")
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "El formato del correo no es válido.")
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Error inesperado: ${e.message}")
                }
            }
        }
    }

    // CERRAR SESIÓN
    fun logout() {
        auth.signOut()
        _uiState.update { AuthUiState() }
    }

    // Limpiar errores cuando el usuario empieza a escribir
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // Validaciones Login
    private fun validateLoginFields(email: String, password: String): String? {
        if (email.isBlank()) return "El correo es obligatorio."
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return "Ingresa un correo válido."
        if (password.isBlank()) return "La contraseña es obligatoria."
        if (password.length < 6) return "La contraseña debe tener al menos 6 caracteres."
        return null
    }

    // Validaciones Registro
    private fun validateRegisterFields(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): String? {
        if (name.isBlank()) return "El nombre es obligatorio."
        if (name.length < 3) return "El nombre debe tener al menos 3 caracteres."
        if (email.isBlank()) return "El correo es obligatorio."
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return "Ingresa un correo válido."
        if (password.isBlank()) return "La contraseña es obligatoria."
        if (password.length < 6) return "La contraseña debe tener al menos 6 caracteres."
        if (password != confirmPassword) return "Las contraseñas no coinciden."
        return null
    }
}