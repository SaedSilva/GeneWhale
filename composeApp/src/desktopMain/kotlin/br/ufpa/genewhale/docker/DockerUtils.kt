package br.ufpa.genewhale.docker

object DockerUtils {
    const val IDENTIFIER = "PAPANGENOME"

    fun convertPathForDocker(windowsPath: String): String {
        val path = windowsPath.replace("\\", "/")
        if (path.length > 2 && path[1] == ':') {
            val driveLetter = path[0].lowercaseChar()
            return "/" + driveLetter + path.substring(2)
        }
        return path
    }
}