package co.edu.unab.carlossaulvillabona.exploraapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lint.kotlin.metadata.Visibility

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onBackClick: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel()
) {
    // 1. Variables de estado (corregidas para que coincidan con el uso)
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val uiState by authViewModel.uiState.collectAsStateWithLifecycle()

    val primaryOrange = Color(0xFFE45D25)
    val lightGrayBg = Color(0xFFF8F9FE)
    val inputBg = Color(0xFFE5E5EA)

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) onRegisterSuccess()
    }

    Surface(modifier = Modifier.fillMaxSize(), color = lightGrayBg) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón Atrás
            IconButton(onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.Start)
                    .offset(x = (-12).dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = primaryOrange)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("Explorando Colombia", color = primaryOrange, fontSize = 22.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(32.dp))
            Text("Crea tu cuenta", fontSize = 32.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start))
            Text("Empieza tu aventura por el realismo mágico", fontSize = 16.sp,
                color = Color.Gray, modifier = Modifier.padding(top = 8.dp).align(Alignment.Start))

            Spacer(modifier = Modifier.height(32.dp))

            // Error de Firebase
            if (uiState.errorMessage != null) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, null, tint = Color(0xFFD32F2F), modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(uiState.errorMessage!!, color = Color(0xFFD32F2F), fontSize = 13.sp)
                    }
                }
            }

            // CAMPOS DE TEXTO
            Column(modifier = Modifier.fillMaxWidth()) {

                // Nombre
                RegisterField(
                    value = name,
                    onValueChange = { name = it },
                    label = "NOMBRE COMPLETO",
                    icon = Icons.Default.Person,
                    inputBg = inputBg
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Correo
                RegisterField(
                    value = email,
                    onValueChange = { email = it; authViewModel.clearError() },
                    label = "CORREO ELECTRÓNICO",
                    icon = Icons.Default.Email,
                    inputBg = inputBg,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Contraseña
                RegisterField(
                    value = password,
                    onValueChange = { password = it; authViewModel.clearError() },
                    label = "CONTRASEÑA",
                    icon = Icons.Default.Lock,
                    inputBg = inputBg,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Confirmar Contraseña
                RegisterField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it; authViewModel.clearError() },
                    label = "CONFIRMAR CONTRASEÑA",
                    icon = Icons.Default.Refresh,
                    inputBg = inputBg,
                    isPassword = true,
                    passwordVisible = confirmPasswordVisible,
                    onPasswordVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Checkbox Términos
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Checkbox(checked = acceptedTerms,
                    onCheckedChange = { acceptedTerms = it },
                    colors = CheckboxDefaults.colors(checkedColor = primaryOrange))
                Text(
                    text = buildAnnotatedString {
                        append("Acepto los ")
                        withStyle(SpanStyle(color = primaryOrange, fontWeight = FontWeight.Bold)) {
                            append("términos y condiciones")
                        }
                        append(" así como el tratamiento de datos personales.")
                    },
                    fontSize = 12.sp, color = Color.Gray, lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Registrar
            Button(
                onClick = { authViewModel.register(name, email, password, confirmPassword) },
                enabled = !uiState.isLoading && acceptedTerms,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = if (acceptedTerms) listOf(primaryOrange, Color(0xFFFF8A65))
                                else listOf(Color.Gray, Color.LightGray)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(28.dp), strokeWidth = 2.dp)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Registrarse", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(24.dp), tint = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                Text("¿Ya tienes una cuenta? ", color = Color.Gray, fontSize = 14.sp)
                Text("Inicia sesión", color = primaryOrange, fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToLogin() })
            }
        }
    }
}

// COMPONENTE DE CAMPO DE TEXTO REUTILIZABLE (MEJORADO)
@Composable
fun RegisterField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    inputBg: Color,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityChange: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column {
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(28.dp)),
            leadingIcon = { Icon(icon, null, tint = Color.Gray) },
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = onPasswordVisibilityChange) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null, tint = Color.Gray
                        )
                    }
                }
            },
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else keyboardOptions,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBg,
                unfocusedContainerColor = inputBg,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )
    }
}