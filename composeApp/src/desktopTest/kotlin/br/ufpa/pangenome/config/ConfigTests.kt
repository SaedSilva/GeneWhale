package br.ufpa.pangenome.config

import br.ufpa.pangenome.config.params.PanarooParams
import kotlin.test.Test
import kotlin.test.asserter


class ConfigTests {
    @Test
    fun testSaveConfig() {
        Config.save("panaroo.json", PanarooParams(
            removeInvalidGenes = true
        ))
    }

    @Test
    fun testLoadConfig() {
        val panaroo = Config.load<PanarooParams>("panaroo.json")
        asserter.assertNotNull("s", panaroo)
    }
}