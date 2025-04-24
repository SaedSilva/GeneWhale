package br.ufpa.genewhale.web

import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class WebServiceJavaTests {
    @Test
    fun testGetLatestVersion() {
        runBlocking {
            val webService = WebServiceJavaImpl()
            val version = webService.getLatestVersion()
        }
    }
}