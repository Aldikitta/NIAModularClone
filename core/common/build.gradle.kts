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

    // TODO add module testing
//    testImplementation(project(":core:testing"))
}