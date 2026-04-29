package co.edu.unab.carlossaulvillabona.exploraapp.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onAddPlace: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explora Colombia") }, // Corregido: Debe llevar Text()
                actions = {

                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAddPlace()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Lugar")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            Text(
                text = "Bienvenido a la pantalla de inicio",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}