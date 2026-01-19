@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  id("com.android.library")
  id("kotlin-parcelize")
  id("app.cash.paparazzi")
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.compose.multiplatform)
}

apply(from = "../../../gradle_include/compose.gradle")

apply(from = "../../../gradle_include/circuit.gradle")

android {
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  namespace = "io.newm.feature.login"

  defaultConfig {
    minSdk = libs.versions.android.minSdk.get().toInt()
    resourcePrefix = "login"
  }

  lint { baseline = file("lint-baseline.xml") }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

kotlin {
  androidTarget()

  wasmJs { browser {} }

  jvm("desktop")

  sourceSets {
    androidMain.dependencies {
      implementation(libs.androidx.core.ktx)
      implementation(libs.androidx.material)
      implementation(libs.androidx.material.icons.extended)
      implementation(libs.koin.android)
      implementation(libs.recaptcha)
      implementation(project(Modules.coreResources))
      implementation(project(Modules.coreTheme))
      implementation(project(Modules.coreUiUtils))
      implementation(project(Modules.shared))
      implementation(project(Modules.sharedComposeFeatures))
      implementation(compose.material)
    }
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
}

dependencies {
  testImplementation(libs.junit)
  testImplementation(libs.test.parameter.injector)
  testImplementation(project(Modules.testUtils))
  testImplementation(compose.components.resources)

  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.test.junit)
}
