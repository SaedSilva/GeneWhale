import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.1.20"
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(libs.kotlin.test)

            val fileKitVersion = "0.10.0-beta01"

            implementation("io.github.vinceglb:filekit-core:$fileKitVersion")
            implementation("io.github.vinceglb:filekit-dialogs:$fileKitVersion")
            implementation("io.github.vinceglb:filekit-dialogs-compose:$fileKitVersion")
            implementation("io.github.vinceglb:filekit-coil:$fileKitVersion")

//            implementation("io.ktor:ktor-client-core:3.1.2")
//            implementation("io.ktor:ktor-client-cio:3.1.2")

            implementation("io.insert-koin:koin-compose:4.0.3")
            implementation("io.insert-koin:koin-compose-viewmodel:4.0.3")
            implementation("io.insert-koin:koin-compose-viewmodel-navigation:4.0.3")

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha12")
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}


compose.desktop {
    application {
        mainClass = "br.ufpa.pangenome.MainKt"

        nativeDistributions {
            buildTypes.release.proguard {
                version.set("7.7.0")
            }

            includeAllModules = true

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "br.ufpa.pangenome"
            packageVersion = "1.0.0"
        }
    }
}



