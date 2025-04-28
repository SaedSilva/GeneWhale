package br.ufpa.genewhale.docker

import br.ufpa.genewhale.utils.Docker.IDENTIFIER
import br.ufpa.genewhale.utils.Docker.convertPathForDocker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

/**
 * Service to manage the Panaroo process using Docker.
 * This class provides methods to start, stop, and remove the Panaroo Docker container.
 * It also handles logging and process management.
 * @param dispatcher The coroutine dispatcher to use for background operations.
 */
class PanarooService(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val _log = MutableSharedFlow<String>(100)
    val logs = _log.asSharedFlow()
    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    private var currentProcess: Process? = null

    /**
     * Basic usage of the Panaroo tool.
     * @param input The input directory containing GFF files.
     * @param output The output directory for results.
     * @param memory Optional memory limit in MB for the Docker container.
     * @param parameters Additional parameters for the Panaroo command.
     */
    suspend fun basicUsage(
        input: String,
        output: String,
        memory: Long? = null,
        parameters: List<String> = emptyList()
    ) {
        if (currentProcess?.isAlive != true) {
            withContext(dispatcher) {
                _isRunning.emit(true)
                val unixInput = convertPathForDocker(input)
                val unixOutput = convertPathForDocker(output)

                val command = mutableListOf(
                    "docker", "run", "--rm", "-i", "--name", "${IDENTIFIER}_panaroo",
                    "-v", "$unixInput:/app/gff",
                    "-v", "$unixOutput:/app/results",
                    "saedss/panaroo:latest",
                    "bash", "-c"
                ).apply {
                    val joinedParams = parameters.joinToString(" ")
                    add("source ~/.bashrc && conda activate panaroo && cd /app && panaroo -i gff/*.gff -o results $joinedParams")
                    memory?.let { add(2, "--memory=${it}m") }
                }

                val builder = ProcessBuilder(command)

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

    /**
     * Stops the currently running Panaroo process.
     */
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

    /**
     * Removes the Panaroo Docker container.
     */
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