package br.ufpa.pangenome.docker

interface IDockerRepository {
    suspend fun isRunning(): Boolean
}