import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
	kotlin(Plugins.multiplatform)
	kotlin(Plugins.serialization)
	id(Plugins.kotlinxSerialization)
	id(Plugins.androidLibrary)
	id(Plugins.sqlDelight)
	id("com.google.devtools.ksp") version "1.9.21-1.0.15"
	id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-22"
}

android {
	compileSdk = Versions.androidCompileSdk
	sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
	namespace = "io.newm.shared"
	defaultConfig {
		minSdk = Versions.androidMinSdk
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
}
dependencies {
	implementation("androidx.security:security-crypto-ktx:1.1.0-alpha06")
//	implementation("com.google.android.recaptcha:recaptcha:18.4.0")
//	implementation("com.google.android.gms:play-services-recaptcha:17.0.1")
	testImplementation("io.mockk:mockk:1.13.9") // Check for the latest version on the MockK GitHub repository
}

kotlin {
	androidTarget()

	val xcf = XCFramework()
	listOf(
		iosArm64(),
		iosSimulatorArm64()
	).forEach {
		it.binaries.framework {
			baseName = "shared"
			xcf.add(this)
		}
	}

	sourceSets {
		val commonMain by getting {
			dependencies {
				implementation(Kotlin.coroutinesCore)
				implementation(Kotlin.stdlib)
				implementation(SqlDelight.runtime)
				implementation(SqlDelight.coroutinesExtensions)
				api(Koin.core)
				api(Log.kermit)
				implementation(Ktor.clientLogging)
				implementation(Ktor.ktorClientCore)
				implementation(Ktor.ktorClientCIO)
				implementation(Ktor.clientContentNegotiation)
				implementation(Ktor.kotlinXJson)
				implementation(Ktor.clientAuth)
				implementation("com.liftric:kvault:1.12.0")
			}
		}
		val commonTest by getting {
			dependencies {
				implementation(Koin.test)
				implementation(Kotlin.coroutinesTest)
				implementation(kotlin("test-common"))
				implementation(kotlin("test-annotations-common"))
			}
		}
		val androidMain by getting {
			dependencies {
				implementation(SqlDelight.androidDriver)
				implementation(Ktor.clientAndroid)
			}
		}

		named("androidUnitTest") {
			dependencies {
				implementation(kotlin("test-junit"))
				implementation("junit:junit:4.13.2")
				implementation("io.mockk:mockk:1.13.9")
			}
		}

		val iosArm64Main by getting
		val iosSimulatorArm64Main by getting
		val iosMain by creating {
			dependsOn(commonMain)
			iosArm64Main.dependsOn(this)
			iosSimulatorArm64Main.dependsOn(this)
//			val recaptcha by cinterops.creating {
//				defFile(project.file("src/nativeInterop/cinterop/recaptcha.def"))
//			}
			dependencies {
				implementation(Ktor.iosDarwin)
				implementation(SqlDelight.nativeDriver)
//				implementation(recaptcha)
			}
		}
		val iosSimulatorArm64Test by getting
		val iosArm64Test by getting
		val iosTest by creating {
			dependsOn(commonTest)
			iosSimulatorArm64Test.dependsOn(this)
			iosArm64Test.dependsOn(this)
			dependencies {
				implementation(Kotlin.coroutinesTest)
				implementation(kotlin("test-common"))
				implementation(kotlin("test-annotations-common"))
			}
		}

		all {
			languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
		}
	}
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions {
		jvmTarget = "11"
	}
}

sqldelight {
	database("NewmDatabase") {
		packageName = "io.newm.shared.db.cache"
		sourceFolders = listOf("sqldelight")
		version = 2
	}
}

kotlin.sourceSets.all {
	languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}
