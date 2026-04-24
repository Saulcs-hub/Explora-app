package co.edu.unab.carlossaulvillabona.exploraapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Destino(
    val nombre: String,
    val lugar: String,
    val rating: String,
    val emoji: String
)

@Composable
fun HomeScreen(
    userName: String = "Carlos Saúl",
    onNavigateToProfile: () -> Unit = {}
) {
    val primaryOrange = Color(0xFFE45D25)
    val lightGrayBg = Color(0xFFF8F9FE)

    val destinos = listOf(
        Destino("Amazonia", "Leticia, Amazonas", "4.9", "🌿"),
        Destino("Ciudad Perdida", "Santa Marta", "4.8", "🏔"),
        Destino("Tayrona", "Magdalena", "4.7", "🏖"),
        Destino("Cartagena", "Bolívar", "4.8", "🏰"),
    )

    val categorias = listOf("Todos", "Naturaleza", "Cultura", "Playa")
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    Surface(modifier = Modifier.fillMaxSize(), color = lightGrayBg) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Header naranja
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryOrange)
                    .padding(horizontal = 20.dp)
                    .padding(top = 48.dp, bottom = 20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Buenos días,",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 13.sp
                            )
                            Text(
                                text = userName,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userName.take(2).uppercase(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Barra de búsqueda
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(28.dp))
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = primaryOrange,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Buscar destinos...", color = Color.Gray, fontSize = 14.sp)
                        }
                    }
                }
            }

            // Contenido con scroll
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Categorías
                Text("Categorías", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(categorias) { categoria ->
                        val isSelected = categoria == categoriaSeleccionada
                        FilterChip(
                            selected = isSelected,
                            onClick = { categoriaSeleccionada = categoria },
                            label = { Text(categoria, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = primaryOrange,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Destinos populares
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Destinos populares", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Ver todos", color = primaryOrange, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(destinos) { destino ->
                        DestinoCard(destino = destino)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Banner promo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(primaryOrange, Color(0xFFFF8A65))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Text("Café con sabor a Colombia ☕", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Descubre la ruta cafetera, patrimonio cultural de la humanidad.", color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.25f))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Explorar ahora →", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DestinoCard(destino: Destino) {
    Card(
        modifier = Modifier.width(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(Color(0xFFE8F5E9)),
                contentAlignment = Alignment.Center
            ) {
                Text(destino.emoji, fontSize = 36.sp)
            }
            Column(modifier = Modifier.padding(10.dp)) {
                Text(destino.nombre, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(destino.lugar, color = Color.Gray, fontSize = 11.sp)
                Text("★ ${destino.rating}", color = Color(0xFFE45D25), fontSize = 11.sp)
            }
        }
    }
}