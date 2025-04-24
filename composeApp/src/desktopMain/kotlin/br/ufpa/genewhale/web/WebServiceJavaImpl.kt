package br.ufpa.genewhale.web

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URI

class WebServiceJavaImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WebService {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getLatestVersion(): String? {
        return withContext(dispatcher) {
            try {
                val url = URI(GITHUB_API_URL).toURL()
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                val value = conn.inputStream.bufferedReader().use { it.readText() }
                val response = json.decodeFromString<GithubResponse>(value)
                response.name
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    companion object {
        //TODO Update this URL to the real URL
        private const val GITHUB_API_URL = "https://api.github.com/repos/vitejs/vite/releases/latest"
    }
}

@Serializable
data class GithubResponse(
    val name: String,
    @SerialName("tag_name") val tagName: String
)