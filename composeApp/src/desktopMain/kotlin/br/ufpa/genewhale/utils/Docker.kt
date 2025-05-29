package br.ufpa.genewhale.utils

/**
 * DockerUtils object to handle docker functions
 */
object Docker {
    const val IDENTIFIER = "GENEWHALE"

    /**
     * Convert a Windows path to a Docker path
     * @param windowsPath the Windows path to convert
     * @return the Unix path
     */
    fun convertPathForDocker(windowsPath: String): String {
        val path = windowsPath.replace("\\", "/")
        if (path.length > 2 && path[1] == ':') {
            val driveLetter = path[0].lowercaseChar()
            return "/" + driveLetter + path.substring(2)
        }
        return path
    }
}