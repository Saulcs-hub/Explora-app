plugins {
    // Usamos solo los alias que ya tienes configurados en tu catálogo de librerías
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // El de Google Services lo dejamos con ID si no lo tienes en el catálogo libs
    id("com.google.gms.google-services")
}

android {
    namespace = "co.edu.unab.carlossaulvillabona.exploraapp"
    compileSdk = 35 // Cambié de 36 a 35 porque 36 es una versión "Preview" que puede dar errores

    defaultConfig {
        applicationId = "co.edu.unab.carlossaulvillabona.exploraapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Librerías base de Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Íconos extendidos (para que funcione el de Visibility)
    implementation("androidx.compose.material:material-icons-extended:1.7.0")

    // Navegación
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // Firebase (Usando BOM para gestionar versiones automáticamente)
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    // 2. Librerías de Firebase (SIN los dos puntos al final)
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // ViewModel y Ciclo de vida
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.0")

    // Corrutinas para Firebase
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1")

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
