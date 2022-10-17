plugins {
    id("aldikitta.android.library")
    id("aldikitta.android.library.jacoco")
    id("aldikitta.android.hilt")
}

android {
    namespace = "com.aldikitta.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}