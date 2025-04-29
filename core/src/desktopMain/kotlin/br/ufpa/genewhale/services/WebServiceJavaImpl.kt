package br.ufpa.genewhale.services

import br.ufpa.genewhale.utils.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

class WebServiceJavaImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WebService {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getLatestVersion(): String? {
        return withContext(dispatcher) {
            try {
                val url = urlFromString(GITHUB_API_URL)
                    ?: throw IllegalArgumentException("This url is not valid: $GITHUB_API_URL")
                val conn = url.openHttpURLConnection()
                conn.setToGETRequestMethod()
                val value = conn.receiveValue()
                val response = json.decodeFromString<GithubResponse>(value)
                response.name
            } catch (e: Exception) {
                Logger.addLog(e)
                null
            }
        }
    }

    companion object {
        private const val GITHUB_API_URL = "https://api.github.com/repos/saedsilva/genewhale/releases/latest"
    }

    private fun urlFromString(url: String): URL? = URI(url).toURL()
    private fun HttpURLConnection.setToGETRequestMethod() = { this.requestMethod = "GET" }
    private fun HttpURLConnection.receiveValue() = this.inputStream.bufferedReader().use { it.readText() }
    private fun URL.openHttpURLConnection() = this.openConnection() as HttpURLConnection
}

@Serializable
data class GithubResponse(
    val name: String,
    @SerialName("tag_name") val tagName: String
)