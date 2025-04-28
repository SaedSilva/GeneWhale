package br.ufpa.genewhale.services

interface WebService {
    suspend fun getLatestVersion(): String?
}