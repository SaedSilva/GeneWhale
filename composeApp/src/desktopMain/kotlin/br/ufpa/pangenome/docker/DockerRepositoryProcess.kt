package br.ufpa.pangenome.docker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DockerRepositoryProcess: IDockerRepository {
    override suspend fun isRunning(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val process = ProcessBuilder("docker", "info")
                    .redirectErrorStream(true)
                    .start()

                val exitCode = process.waitFor()

                exitCode == 0
            } catch (e: Exception) {
                false
            }
        }
    }
}