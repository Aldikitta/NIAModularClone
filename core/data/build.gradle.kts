plugins {
    id("aldikitta.android.library")
    id("aldikitta.android.library.jacoco")
    id("aldikitta.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.aldikitta.data"
}

dependencies {
    // TODO module core:data. Configure module
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))

    testImplementation(project(":core:testing"))
//    testImplementation(project(":core:datastore-test"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
}