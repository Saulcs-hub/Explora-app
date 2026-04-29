package co.edu.unab.carlossaulvillabona.exploraapp.ui.elements

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun AddTouristicPlaceScreen (onBackClick: () -> Unit) {
    Text(fontSize = 50.sp, text = "Agregar Lugar Turistico")
    IconButton(onClick = onBackClick) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
    }
}