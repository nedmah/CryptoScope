pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://repo1.maven.org/maven2/") }
    }
}

rootProject.name = "CryptoScope"
include(":app")
include(":common-ui")
include(":core")
include(":crypto-listing")
include(":crypto-info")
include(":wallet")
include(":news")
