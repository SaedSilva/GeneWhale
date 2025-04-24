package br.ufpa.genewhale.web

import kotlinx.coroutines.runBlocking
import kotlin.test.Asserter
import kotlin.test.Test
import kotlin.test.asserter

class WebServiceJavaTests {
    @Test
    fun testGetLatestVersion() {
        runBlocking {
            val webService = WebServiceJavaImpl()
            val version = webService.getLatestVersion()
            asserter.assertNotNull(
                "The latest version should not be null.",
                version
            )
        }
    }
}