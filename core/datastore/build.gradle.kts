import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

// TODO: Remove once https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("aldikitta.android.library")
    id("aldikitta.android.library.jacoco")
    id("aldikitta.android.hilt")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.aldikitta.datastore"

    defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
    }
}

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }
                val kotlin by registering {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    testImplementation(project(":core:testing"))

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.dataStore.core)
    implementation(libs.protobuf.kotlin.lite)
}