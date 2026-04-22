package co.edu.unab.carlossaulvillabona.exploraapp


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val user = FirebaseAuth.getInstance().currentUser
    val primaryOrange = Color(0xFFE45D25)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explorando Colombia", fontWeight = FontWeight.Bold) },
                actions = {
                    // 🚪 Botón cerrar sesión
                    IconButton(onClick = {
                        authViewModel.logout()
                        onLogout()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar sesión", tint = primaryOrange)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("¡Bienvenido! 🇨🇴", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = user?.displayName ?: user?.email ?: "Explorador",
                fontSize = 18.sp, color = Color.Gray
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text("Aquí irá el contenido de tu app",
                fontSize = 14.sp, color = Color.LightGray)
        }
    }
}