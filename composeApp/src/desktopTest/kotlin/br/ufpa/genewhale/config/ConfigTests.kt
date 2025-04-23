package br.ufpa.genewhale.config

import br.ufpa.genewhale.config.params.PanarooParamsConfig
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.asserter


class ConfigTests {
    @Test
    fun testSaveConfig() {
        runBlocking {
            Config.save(Config.PANAROO_CONFIG_FILE, PanarooParamsConfig(
                removeInvalidGenes = true,
                threads = 4,
            ))
        }
    }

    @Test
    fun testLoadConfig() {
        runBlocking {
            val panaroo = Config.load<PanarooParamsConfig>(Config.PANAROO_CONFIG_FILE)
            asserter.assertNotNull("s", panaroo)
        }
    }
}