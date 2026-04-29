package co.edu.unab.carlossaulvillabona.exploraapp.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Preview(showBackground = true, name = "Vista Previa Agregar Lugar")
@Composable
fun PreviewAddTouristicPlaceScreen() {
    AddTouristicPlaceScreen(onBackClick = {})
}

// ─── Brand colors ────────────────────────────────────────────────────────────
private val BrandRed      = Color(0xFFCC4A28)
private val BrandRedLight = Color(0xFFE05A35)
private val PageBg        = Color(0xFFF5F3F0)
private val FieldBg       = Color(0xFFEAE8E4)
private val LabelColor    = Color(0xFF8A8580)
private val HintColor     = Color(0xFFB0ADA8)



@Composable

fun AddTouristicPlaceScreen(onBackClick: () -> Unit) {

    // Estados

    var placeName   by remember { mutableStateOf("") }
    var department  by remember { mutableStateOf("") }
    var city        by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        containerColor = PageBg,
        // ── Top bar ──────────────────────────────────────────────────────────
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = BrandRed
                    )
                }
                Text(
                    text = "Add Place",
                    color = BrandRed,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
        },
        // ── Publish button ────────────────────────────────────────────────────
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = {  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandRed)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Publicar",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            // ── Hero card ─────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(BrandRedLight, BrandRed)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 28.dp)
                ) {
                    Text(
                        text = "Comparte tu\ndescubrimiento",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Ayuda a otros viajeros a encontrar los tesoros\nescondidos de nuestra tierra.",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.88f),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Form fields ───────────────────────────────────────────────────
            PlaceField(
                label = "NOMBRE DEL LUGAR",
                value = placeName,
                onValueChange = { placeName = it },
                placeholder = "Ej: Cascada del Fin del Mundo"
            )

            Spacer(modifier = Modifier.height(20.dp))

            PlaceField(
                label = "DEPARTAMENTO",
                value = department,
                onValueChange = { department = it },
                placeholder = "Ej: Putumayo"
            )

            Spacer(modifier = Modifier.height(20.dp))

            PlaceField(
                label = "CIUDAD",
                value = city,
                onValueChange = { city = it },
                placeholder = "Ej: Mocoa"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Description (multi-line) ──────────────────────────────────────
            Text(
                text = "DESCRIPCIÓN",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                color = LabelColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                placeholder = {
                    Text(
                        text = "Cuéntanos por qué este lugar es especial…",
                        color = HintColor,
                        fontSize = 15.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(14.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = FieldBg,
                    focusedContainerColor   = FieldBg,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor   = Color.Transparent,
                    cursorColor             = BrandRed,
                    focusedTextColor        = Color(0xFF2C2C2A),
                    unfocusedTextColor      = Color(0xFF2C2C2A)
                ),
                textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                maxLines = 6
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ─── Reusable single-line field ───────────────────────────────────────────────
@Composable
private fun PlaceField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp,
            color = LabelColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder, color = HintColor, fontSize = 15.sp)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = FieldBg,
                focusedContainerColor   = FieldBg,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor   = Color.Transparent,
                cursorColor             = BrandRed,
                focusedTextColor        = Color(0xFF2C2C2A),
                unfocusedTextColor      = Color(0xFF2C2C2A)
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
            singleLine = true
        )
    }
}