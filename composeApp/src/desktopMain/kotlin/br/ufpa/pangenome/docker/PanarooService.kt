package br.ufpa.pangenome.docker

import br.ufpa.pangenome.docker.DockerUtils.IDENTIFIER
import br.ufpa.pangenome.docker.DockerUtils.convertPathForDocker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class PanarooService(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val _log = MutableSharedFlow<String>(100)
    val logs = _log.asSharedFlow()
    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    private var currentProcess: Process? = null

    /**
     * Starts the Panaroo process using Docker.
     * @param input The input directory containing GFF files.
     * @param output The output directory for results.
     * @param memory Optional memory limit in MB for the Docker container.
     * @param parameters Additional parameters for the Panaroo command.
     */
    suspend fun start(
        input: String,
        output: String,
        memory: Long? = null,
        parameters: List<String> = emptyList()
    ) {
        if (currentProcess == null || !currentProcess!!.isAlive) {
            withContext(dispatcher) {
                _isRunning.emit(true)
                val unixInput = convertPathForDocker(input)
                val unixOutput = convertPathForDocker(output)

                @Suppress("NAME_SHADOWING")
                val parameters = parameters.flatMap { it.split(" ") }
                val command = mutableListOf(
                    "docker", "run", "--rm", "-i", "--name", "${IDENTIFIER}_panaroo",
                    "-v", "$unixInput:/app/gff",
                    "-v", "$unixOutput:/app/results",
                    "saedss/panaroo:latest",
                    "source ~/.bashrc && conda activate panaroo && cd /app && panaroo -i gff/*.gff -o results"
                )
                command.addAll(parameters)
                memory?.let {
                    command.add(2, "--memory=${it}m")
                }

                val builder = ProcessBuilder(
                    command
                )

                builder.redirectErrorStream(true)
                try {

                    currentProcess = builder.start()

                    _log.emit("Starting process [PID: ${currentProcess?.pid()}] with command:\n${command.joinToString(" ")}")

                    currentProcess?.inputStream?.bufferedReader().use { reader ->
                        reader?.lineSequence()?.forEach { line -> _log.emit(line) }
                    }

                    val exitCode = currentProcess?.waitFor()

                    if (exitCode != 0) {
                        _log.emit("Error: Process exited with code $exitCode")
                    } else {
                        _log.emit("Process completed successfully.")
                    }

                } catch (e: Exception) {
                    _log.emit("Error: ${e.message}")
                } finally {
                    currentProcess?.destroy()
                    currentProcess = null
                    _isRunning.emit(false)
                }
            }
        } else {
            _log.emit("Process is already running.")
        }
    }

    suspend fun stop() = withContext(Dispatchers.IO) {
        _isRunning.emit(false)
        try {
            currentProcess?.destroy()
            currentProcess = null
            _log.emit("Local process stopped manually.")
        } catch (e: Exception) {
            _log.emit("Error stopping local process: ${e.message}")
        }

        try {
            val command = listOf("docker", "stop", "${IDENTIFIER}_panaroo")
            val process = ProcessBuilder(command).start()
            process.waitFor()
            _log.emit("Docker container stopped.")
        } catch (e: Exception) {
            _log.emit("Error stopping container: ${e.message}")
        }
    }

    suspend fun remove() = withContext(Dispatchers.IO) {
        try {
            val command = listOf("docker", "rm", "${IDENTIFIER}_panaroo")
            val process = ProcessBuilder(command).start()
            process.waitFor()
            _log.emit("Docker container removed.")
        } catch (e: Exception) {
            _log.emit("Error removing container: ${e.message}")
        }
    }
}