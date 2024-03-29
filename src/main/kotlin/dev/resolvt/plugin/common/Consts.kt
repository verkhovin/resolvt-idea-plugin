package dev.resolvt.plugin.common

object Consts {
    private const val apiUrl = "/api"
    const val authUrl = "/plugins/auth/code"
    const val accessTokenUrl = "${apiUrl}/auth/access-token"
    const val meUrl = "${apiUrl}/me"
    const val debtsUrl = "${apiUrl}/debts"
    const val repositoriesUrl = "${apiUrl}/repositories"

    const val PROJECT_REMOTE_PROPERTY_KEY = "dev.resolvt.idea-integration.projectRemote"
    const val SAVED_TITLE_PROPERTY_KEY = "dev.resolvt.idea-integration.title"
    const val SAVED_DESCRIPTION_PROPERTY_KEY = "dev.resolvt.idea-integration.description"
}