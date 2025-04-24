package br.ufpa.genewhale.web

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

class WebServiceJavaImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WebService {
    override suspend fun getLatestVersion(): String {
        return withContext(dispatcher) {
            sleep(1000)
            return@withContext "1.0.1"
        }
    }
}