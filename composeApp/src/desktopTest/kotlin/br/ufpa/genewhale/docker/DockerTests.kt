package br.ufpa.genewhale.docker

import kotlin.test.Test
import kotlin.test.asserter

class DockerTests {

    @Test
    fun testConvertPathForDocker() {
        val windowsPath = "C:\\Users\\User\\Documents\\file.txt"
        val expectedDockerPath = "/c/Users/User/Documents/file.txt"
        val convertedPath = DockerUtils.convertPathForDocker(windowsPath)
        asserter.assertEquals(
            "The converted path should match the expected Docker path.",
            expectedDockerPath,
            convertedPath
        )
    }
}