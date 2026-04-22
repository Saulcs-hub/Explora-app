package co.edu.unab.carlossaulvillabona.exploraapp

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)