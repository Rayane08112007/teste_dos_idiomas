plugins {
    id("com.android.application")

}

android {
    namespace = "com.example.nossotcc"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.nossotcc"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    // Se usar biblioteca para chat ou websockets, adicionar aqui
    // Exemplo: Retrofit para API
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Se usar coroutines (Kotlin) ou threads
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation(libs.activity)
    implementation(libs.constraintlayout)
}
