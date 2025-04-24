package br.ufpa.genewhale.web

interface WebService {
    suspend fun getLatestVersion(): String
}