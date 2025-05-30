import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
}

val appVersionName get() = project.properties["appVersionName"].toString()
val appVersionCode get() = project.properties["appVersionCode"].toString().toInt()

kotlin {
    jvmToolchain(21)
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.voyager.navigator)
            implementation(libs.multiplatformSettings)
            implementation(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.kotlinx.coroutines.test)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation("org.seleniumhq.selenium:selenium-java:4.33.0")
            implementation("io.github.bonigarcia:webdrivermanager:6.1.0")
            implementation ("io.github.microutils:kotlin-logging-jvm:2.1.21")
            implementation("ch.qos.logback:logback-classic:1.4.12")
        }

    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {

            nativeDistributions {
                packageName = "Cub"
                packageVersion = appVersionName
                linux {
                    iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
                    rpmLicenseType = "Copyright"
                    menuGroup = "Cub"
                    appCategory = "Office"
                    targetFormats(TargetFormat.Deb, TargetFormat.Rpm, TargetFormat.AppImage)

                }
                windows {
                    iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
                    perUserInstall = true
                    menuGroup = "Cub"
                    upgradeUuid = "d385c37d-d496-46d9-a85b-a8818ce896f7".uppercase()
                    targetFormats(TargetFormat.Msi, TargetFormat.Exe, TargetFormat.AppImage)

                }
                macOS {
                    iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
                    bundleID = "dev.mateuy.cub.desktopApp"
                    targetFormats(TargetFormat.Dmg)

                }
            }
            buildTypes.release.proguard {
                version.set("7.4.0")
                configurationFiles.from(project.file("compose-desktop.pro"))
                isEnabled = false
            }
        }
    }
}