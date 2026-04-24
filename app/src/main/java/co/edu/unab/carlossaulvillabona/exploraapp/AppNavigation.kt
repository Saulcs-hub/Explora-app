package co.edu.unab.carlossaulvillabona.exploraapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.LOGIN,
        modifier = Modifier.fillMaxSize()
    ) {
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
            )
        }

        composable(route = NavRoutes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {

                },
                onNavigateToLogin = {
                    navController.navigate(NavRoutes.LOGIN)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = NavRoutes.HOME) {
            HomeScreen(userName = "Carlos Saúl")
        }
    }
}