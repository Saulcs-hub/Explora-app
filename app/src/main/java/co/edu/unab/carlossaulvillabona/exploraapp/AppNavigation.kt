package co.edu.unab.carlossaulvillabona.exploraapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.unab.carlossaulvillabona.exploraapp.ui.elements.HomeScreen
import co.edu.unab.carlossaulvillabona.exploraapp.ui.elements.LoginScreen
import co.edu.unab.carlossaulvillabona.exploraapp.ui.elements.RegisterScreen
import co.edu.unab.carlossaulvillabona.exploraapp.ui.elements.AddTouristicPlaceScreen // Asegúrate de importar tu nueva pantalla
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val auth = Firebase.auth

    val startDestination = if (auth.currentUser != null) NavRoutes.HOME else NavRoutes.LOGIN

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        // --- PANTALLA DE LOGIN ---
        composable(route = NavRoutes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(NavRoutes.REGISTER)
                }
                // Quitamos onAddPlace de aquí, no pertenece al Login
            )
        }

        // --- PANTALLA DE REGISTRO ---
        composable(route = NavRoutes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(NavRoutes.LOGIN)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // --- PANTALLA DE INICIO (HOME) ---
        composable(route = NavRoutes.HOME) {
            HomeScreen(
                onLogout = {
                    navController.navigate(NavRoutes.LOGIN) {
                        popUpTo(NavRoutes.HOME) { inclusive = true }
                    }
                },
                // MOVIMOS esto aquí:
                onAddPlace = {
                    navController.navigate(NavRoutes.ADD_TOURISTIC_PLACE)
                }
            )
        }

        // --- PANTALLA DE AGREGAR LUGAR (LA QUE FALTABA) ---
        composable(route = NavRoutes.ADD_TOURISTIC_PLACE) {
            AddTouristicPlaceScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}