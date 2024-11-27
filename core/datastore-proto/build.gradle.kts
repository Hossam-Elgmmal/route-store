plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "com.route.datastore"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    //
    implementation(libs.androidx.datastore)
    implementation("com.google.protobuf:protobuf-kotlin-lite:4.26.1")
    //
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
kapt {
    correctErrorTypes = true
}
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.26.1"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}