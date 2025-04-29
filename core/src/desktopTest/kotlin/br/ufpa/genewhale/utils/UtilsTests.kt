package br.ufpa.genewhale.utils

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.junit.Assert
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

    @Test
    fun `test Long toMB`() {
        val bytes = 10_485_760L // 10 MB
        Assert.assertEquals(10L, bytes.toMB())

        val zeroBytes = 0L
        Assert.assertEquals(0L, zeroBytes.toMB())
    }

    @Test
    fun `test Long to Bytes`() {
        val megabytes = 10L // 10 MB
        Assert.assertEquals(10_485_760L, megabytes.toBytes())

        val zeroMegabytes = 0L
        Assert.assertEquals(0L, zeroMegabytes.toBytes())
    }

    @Test
    fun `test Float toMB`() {
        val maxMemory = 100L // 100 MB
        val percentage = 0.25f // 25%

        Assert.assertEquals(25L, percentage.toMB(maxMemory))

        val fullPercentage = 1.0f
        Assert.assertEquals(100L, fullPercentage.toMB(maxMemory))

        val zeroPercentage = 0.0f
        Assert.assertEquals(0L, zeroPercentage.toMB(maxMemory))
    }

    @Test
    fun `test Float toThreads`() {
        val maxThreads = 8
        val half = 0.5f
        val almostFull = 0.99f

        Assert.assertEquals(4, half.toThreads(maxThreads))
        Assert.assertEquals(8, almostFull.toThreads(maxThreads)) // 7.92 → arredonda para 8

        val zero = 0.0f
        Assert.assertEquals(0, zero.toThreads(maxThreads))
    }

    @Test
    fun `test String isValidFloat`() {
        Assert.assertTrue("123.45".isValidFloat())
        Assert.assertTrue("-123.45".isValidFloat())
        Assert.assertTrue("0".isValidFloat())
        Assert.assertTrue("3.14159".isValidFloat())

        Assert.assertFalse("abc".isValidFloat())
        Assert.assertFalse("".isValidFloat())
        Assert.assertFalse("123abc".isValidFloat())
    }

    @Test
    fun `test String isValidInt`() {
        Assert.assertTrue("123".isValidInt())
        Assert.assertTrue("-123".isValidInt())
        Assert.assertTrue("0".isValidInt())

        Assert.assertFalse("123.45".isValidInt())
        Assert.assertFalse("abc".isValidInt())
        Assert.assertFalse("".isValidInt())
    }

    @Serializable
    internal data class PanarooParamsConfig(
        val removeInvalidGenes: Boolean,
        val threads: Int
    )
}