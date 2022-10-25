plugins {
    id("aldikitta.android.application")
    id("aldikitta.android.application.compose")
    id("aldikitta.android.application.jacoco")
    id("aldikitta.android.hilt")
    id("jacoco")
    id("aldikitta.firebase-perf")
}

android {
    namespace = "com.aldikitta.niamodularclone"
//    compileSdk 33

    defaultConfig {
        applicationId = "com.aldikitta.niamodularclone"
//        minSdk 28
//        targetSdk 33
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = '1.8'
//    }
//    buildFeatures {
//        compose true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion '1.3.2'
//    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
//    implementation(project(":feature:author"))
//    implementation(project(":feature:interests"))
//    implementation(project(":feature:foryou"))
//    implementation(project(":feature:bookmarks"))
//    implementation(project(":feature:topic"))

    implementation(project(":core:ui"))
//    implementation(project(":core:designsystem"))
//
//    implementation(project(":sync:work"))
//    implementation(project(":sync:sync-test"))

    androidTestImplementation(project(":core:testing"))
//    androidTestImplementation(project(":core:datastore-test"))
//    androidTestImplementation(project(":core:data-test"))
    androidTestImplementation(project(":core:network"))

    androidTestImplementation(libs.androidx.navigation.testing)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.profileinstaller)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
}