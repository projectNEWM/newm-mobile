import org.gradle.api.artifacts.dsl.RepositoryHandler

object Repo {

    @JvmStatic
    fun addRepos(handler: RepositoryHandler) {
        handler.gradlePluginPortal()
        handler.google()
        handler.mavenCentral()
    }
}