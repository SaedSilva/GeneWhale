package br.ufpa.pangenome.config

import br.ufpa.pangenome.config.params.PanarooParamsConfig
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.asserter


class ConfigTests {
    @Test
    fun testSaveConfig() {
        runBlocking {
            Config.save(Config.DEFAULT_PANAROO_CONFIG, PanarooParamsConfig(
                removeInvalidGenes = true,
                threads = 4,
            ))
        }
    }

    @Test
    fun testLoadConfig() {
        runBlocking {
            val panaroo = Config.load<PanarooParamsConfig>(Config.DEFAULT_PANAROO_CONFIG)
            asserter.assertNotNull("s", panaroo)
        }
    }
}