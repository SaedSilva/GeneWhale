import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(project(":components"))
            implementation(project(":core"))
            implementation(project(":panaroo"))

            implementation(libs.kotlinx.datetime)

            implementation(libs.kotlin.test)

            implementation(libs.filekit.core)
            implementation(libs.filekit.dialogs)
            implementation(libs.filekit.dialogs.compose)
            implementation(libs.filekit.coil)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.navigation.compose)

            implementation(libs.compose.material.icons.core)
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
        mainClass = "br.ufpa.genewhale.MainKt"

        nativeDistributions {
            macOS {
                iconFile.set(project.file("src/desktopMain/resources/genewhaleicon.icns"))
            }
            windows {
                shortcut = true
                iconFile.set(project.file("src/desktopMain/resources/genewhaleicon.ico"))
            }
            linux {
                iconFile.set(project.file("src/desktopMain/resources/genewhaleicon.png"))
            }

            buildTypes.release.proguard {
                version.set("7.7.0")
                configurationFiles.from(project.file("../compose-desktop.pro"))
            }

            includeAllModules = true

            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Msi,
                TargetFormat.Deb
            )
            packageName = "GeneWhale"
            description = "GeneWhale complete tool for genomes analysis"
            copyright = "© 2025 Saed Silva Sousa. All rights reserved."
            vendor = "Saed Silva Sousa"
            packageVersion = "1.0.0"
            licenseFile.set(project.file("../LICENSE"))
        }
    }
}



