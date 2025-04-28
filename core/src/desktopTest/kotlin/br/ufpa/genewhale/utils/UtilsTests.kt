package br.ufpa.genewhale.utils

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.asserter

class UtilsTests {
    @Test
    fun testConvertPathForDocker() {
        val windowsPath = "C:\\Users\\User\\Documents\\file.txt"
        val expectedDockerPath = "/c/Users/User/Documents/file.txt"
        val convertedPath = Docker.convertPathForDocker(windowsPath)
        asserter.assertEquals(
            "The converted path should match the expected Docker path.",
            expectedDockerPath,
            convertedPath
        )
    }

    @Test
    fun testSaveConfig() {


        runBlocking {
            Config.save(
                Config.PANAROO_CONFIG_FILE, PanarooParamsConfig(
                    removeInvalidGenes = true,
                    threads = 4,
                )
            )
        }
    }

    @Test
    fun testLoadConfig() {
        runBlocking {
            Config.load<PanarooParamsConfig>(Config.PANAROO_CONFIG_FILE)
        }
    }

    @Serializable
    internal data class PanarooParamsConfig(
        val removeInvalidGenes: Boolean,
        val threads: Int
    )
}