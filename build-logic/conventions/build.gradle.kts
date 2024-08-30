plugins {
    alias(libs.plugins.kotlin.dsl)
}
dependencies {
    compileOnly(libs.agp.tools.gradle)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    compileOnly(files(gradleProjectConfig.javaClass.superclass.protectionDomain.codeSource.location))
    compileOnly(files(androidProjectConfig.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        with(libs.plugins.githubapi) {
            register(library.kotlin.pluginId) {
                id = library.kotlin.pluginId
                implementationClass = pluginConfig.versions.kotlin.library.get()

            }

            register(library.android.pluginId) {
                id = library.android.pluginId
                implementationClass = pluginConfig.versions.android.library.get()
            }
        }
    }
}

private inline val Provider<PluginDependency>.pluginId
    get() = get().pluginId