package br.ufpa.pangenome.config

import java.io.File
import javax.swing.filechooser.FileSystemView

object Config {
    fun getDefaultFolder(): File {
        val folder = File(FileSystemView.getFileSystemView().defaultDirectory, "papangenome")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        return folder
    }
}