plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.emulatortest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.emulatortest"
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
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    testOptions {
        managedDevices {
            localDevices {
                create("maxVersion") {
                    device = "Small Phone"
                    apiLevel = defaultConfig.targetSdk!!
                    systemImageSource = "aosp-atd"
                }
            }

            // If the previous test run succeeded and nothing has changed,
            // Gradle thinks there's no need to run it again. Override that.
            afterEvaluate {
                (localDevices.names + listOf("connected")).forEach {
                    tasks.named("${it}DebugAndroidTest") {
                        outputs.upToDateWhen { false }
                    }
                }
            }
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}