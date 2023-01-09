package dev.resolvt.plugin.ide.service

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import dev.resolvt.plugin.client.model.Tokens


class CredentialsService {
    fun saveCredentials(tokens: Tokens, host: String) {
        saveToken(tokens.accessToken, "AccessToken")
        saveToken(tokens.refreshToken, "RefreshToken")
        saveToken(host, "Host")
    }

    fun updateTokens(tokens: Tokens) {
        saveToken(tokens.accessToken, "AccessToken")
        saveToken(tokens.refreshToken, "RefreshToken")
    }

    fun hasCredentials() =
        PasswordSafe.instance.get(createCredentialAttributes("AccessToken")) != null
                && PasswordSafe.instance.get(createCredentialAttributes("RefreshToken")) != null


    fun getAccessToken() = PasswordSafe.instance.getPassword(createCredentialAttributes("AccessToken"))


    fun getRefreshToken() = PasswordSafe.instance.getPassword(createCredentialAttributes("RefreshToken"))

    fun getHost() = PasswordSafe.instance.getPassword(createCredentialAttributes("Host"))

    fun clearTokens() {
        saveToken(null, "AccessToken")
        saveToken(null, "RefreshToken")
        saveToken(null, "Host")
    }

    private fun saveToken(token: String?, name: String) {
        val accessTokenAttributes = createCredentialAttributes(name)
        val accessToken = if (token == null) null else Credentials("", token)
        PasswordSafe.instance.set(accessTokenAttributes, accessToken)
    }

    private fun createCredentialAttributes(key: String) = CredentialAttributes(generateServiceName("Resolvt", key))

}