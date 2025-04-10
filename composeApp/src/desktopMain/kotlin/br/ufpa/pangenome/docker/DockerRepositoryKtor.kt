package br.ufpa.pangenome.docker

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DockerRepositoryKtor(private val client: HttpClient) : IDockerRepository {
    override suspend fun isRunning(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.get("http://localhost:2375/_ping")
                response.status.value == 200
            } catch (e: Exception) {
                println(e)
                false
            }
        }
    }
}