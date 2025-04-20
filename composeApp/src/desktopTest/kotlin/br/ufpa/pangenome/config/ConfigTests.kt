package br.ufpa.pangenome.config

import br.ufpa.pangenome.config.params.PanarooParams
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.asserter


class ConfigTests {
    @Test
    fun testSaveConfig() {
        runBlocking {
            Config.save("panaroo.json", PanarooParams(
                removeInvalidGenes = true,
                threads = 4,
            ))
        }
    }

    @Test
    fun testLoadConfig() {
        runBlocking {
            val panaroo = Config.load<PanarooParams>("panaroo.json")
            asserter.assertNotNull("s", panaroo)
        }
    }
}