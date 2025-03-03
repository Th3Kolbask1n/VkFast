plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose.compiler)
    id("vkid.manifest.placeholders")
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.alexp.vkfast"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.alexp.vkfast"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true

    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.compose.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.compose.livedata)
    implementation(libs.androidx.ui.util)
    implementation(libs.navigation.compose)
    implementation(libs.gson)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.coil)
    implementation(libs.vk.core)
    implementation(libs.vk.api)

    implementation(libs.vkid)
    implementation(libs.onetap.compose)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.appcompat)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation(libs.httpLoggingInterceptor)
    implementation(libs.okHttpClient)
    implementation(libs.retrofit)
    implementation(libs.androidx.storage)
    coreLibraryDesugaring(libs.desugar.jdk.libs.v212)

    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.dagger2)
    ksp(libs.dagger2.compiler)
    ksp(libs.dagger2.android.processor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}


