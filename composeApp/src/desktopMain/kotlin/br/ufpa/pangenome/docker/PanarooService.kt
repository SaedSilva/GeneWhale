package br.ufpa.pangenome.docker

import br.ufpa.pangenome.docker.DockerUtils.IDENTIFIER
import br.ufpa.pangenome.docker.DockerUtils.convertPathForDocker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class PanarooService {


    suspend fun start(input: String, output: String): Flow<String> {
        return withContext(Dispatchers.IO) {
            channelFlow {
                try {
                    val unixInput = convertPathForDocker(input)
                    val unixOutput = convertPathForDocker(output)

                    val command = listOf(
                        "docker", "run", "--rm", "-i", "--name", "${IDENTIFIER}_panaroo",
                        "-v", "$unixInput:/app/gff",
                        "-v", "$unixOutput:/app/results",
                        "saedss/panaroo:latest"
                    )
                    println(command)

                    val builder = ProcessBuilder(
                        command
                    )

                    builder.redirectErrorStream(true)

                    val process = builder.start()

                    val reader = process.inputStream.bufferedReader()

                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        send(line!!)
                    }

                    val exitCode = process.waitFor()

                    if (exitCode != 0) {
                        send("Error: Process exited with code $exitCode")
                    } else {
                        send("Process completed successfully.")
                    }

                    reader.close()

                } catch (e: Exception) {
                    send("Error: ${e.message}")
                } finally {
                    close()
                }
            }.flowOn(Dispatchers.IO)
        }
    }

    suspend fun stop() = withContext(Dispatchers.IO) {
        try {
            val command = listOf("docker", "stop", "${IDENTIFIER}_panaroo")
            val process = ProcessBuilder(command).start()
            process.waitFor()
        } catch (e: Exception) {
            println("Error stopping container: ${e.message}")
        }
    }
}