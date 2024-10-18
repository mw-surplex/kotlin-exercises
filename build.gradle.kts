@file:Suppress("OPT_IN_USAGE")

//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "2.0.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.power-assert") version kotlinVersion
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

object Versions {
    const val kotestVersion = "5.9.1"
    const val kotlinxCoroutinesVersion = "1.7.3"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinxCoroutinesVersion}")
    //implementation("com.sksamuel.aedile:aedile-core:1.3.1") // Needed for CompanyDetailsRepository
}

//tasks.test {
//    useJUnitPlatform()
//}

//tasks.withType<KotlinCompile> {
//    kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers", "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
//}

//java.sourceSets["test"].java {
//    srcDir("src/main/kotlin")
//}

powerAssert {
    functions = listOf("kotlin.assert", "kotlin.test.assertEquals")
}
