import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
	kotlin(Plugins.multiplatform)
	kotlin(Plugins.serialization)
	id(Plugins.kotlinxSerialization)
	id(Plugins.androidLibrary)
	id(Plugins.sqlDelight)
	id("com.google.devtools.ksp") version "1.9.21-1.0.15"
	id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-22"
	id("com.github.gmazzo.buildconfig") version "5.3.5"
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
			}
		}

		val iosArm64Main by getting
		val iosSimulatorArm64Main by getting
		val iosMain by creating {
			dependsOn(commonMain)
			iosArm64Main.dependsOn(this)
			iosSimulatorArm64Main.dependsOn(this)
			dependencies {
				implementation(Ktor.iosDarwin)
				implementation(SqlDelight.nativeDriver)
			}
		}
		val iosSimulatorArm64Test by getting
		val iosTest by creating {
			dependsOn(commonTest)
			iosSimulatorArm64Test.dependsOn(this)
		}

		all {
			languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
		}
	}
}

buildConfig {
	packageName("io.newm.shared.generated")

	val properties: Properties = gradleLocalProperties(rootDir)
	buildConfigField<String>( "STAGING_URL", properties.getProperty("STAGING_URL").replace("\"", ""))
	buildConfigField<String>("PRODUCTION_URL", properties.getProperty("PRODUCTION_URL").replace("\"", ""))
	buildConfigField<String>("GOOGLE_AUTH_CLIENT_ID", properties.getProperty("GOOGLE_AUTH_CLIENT_ID").replace("\"", ""))
	buildConfigField<String>("RECAPTCHA_SITE_KEY", properties.getProperty("RECAPTCHA_SITE_KEY").replace("\"", ""))
	buildConfigField<String>("SENTRY_AUTH_TOKEN", properties.getProperty("SENTRY_AUTH_TOKEN").replace("\"", ""))
	buildConfigField<String>("WALLET_CONNECT_PROJECT_ID", properties.getProperty("WALLET_CONNECT_PROJECT_ID").replace("\"", ""))
	buildConfigField<String>("NEWM_MOBILE_APP_VERSION", "1.0.0")
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
