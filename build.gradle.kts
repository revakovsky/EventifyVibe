plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}
true // Needed to make the Suppress annotation work for the plugins block
