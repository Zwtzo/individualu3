plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "upvictoria.pm_may_ago_2025.iti_271415.pi1u3.guerrero_guerrero"
    compileSdk = 35

    defaultConfig {
        applicationId = "upvictoria.pm_may_ago_2025.iti_271415.pi1u3.guerrero_guerrero"
        minSdk = 24
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



        buildFeatures {
            viewBinding = true // recomendable si usas binding en XML
        }
    }

    dependencies {
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)

        // ML Kit para reconocimiento de texto
        implementation("com.google.mlkit:text-recognition:16.0.0")

        // CameraX
        implementation("androidx.camera:camera-core:1.3.0")
        implementation("androidx.camera:camera-camera2:1.3.0")
        implementation("androidx.camera:camera-lifecycle:1.3.0")
        implementation("androidx.camera:camera-view:1.3.0")

        // Testing
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
    }
}