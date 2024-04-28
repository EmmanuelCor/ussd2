plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.ussd"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ussd"
        minSdk = 25
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {

//    implementation(platform(libs.firebase.bom))
//    implementation(libs.google.cloud.storage)
//    implementation(libs.google.google.auth.library.oauth2.http)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.firestore)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.firebase.auth)
    implementation(libs.play.services)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}