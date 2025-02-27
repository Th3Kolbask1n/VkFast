// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("vkid.manifest.placeholders") version "1.1.0" apply true
    alias(libs.plugins.ksp) apply false

}

vkidManifestPlaceholders {
    // Добавьте плейсхолдеры сокращенным способом. Например, vkidRedirectHost будет "vk.com", а vkidRedirectScheme будет "vk$clientId".
    init(
        clientId = "53047674",
        clientSecret = "nl0WZPstAbvwVdkJZRE5",
    )

}

