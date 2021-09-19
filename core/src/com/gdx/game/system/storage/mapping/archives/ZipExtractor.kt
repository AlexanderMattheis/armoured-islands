package com.gdx.game.system.storage.mapping.archives

import com.gdx.game.system.defaults.Paths.PATHS_SEPARATOR
import com.gdx.game.system.defaults.parameters.InputOutput
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.system.storage.mapping.archives.ThumbnailExtractionPath.getFileDestinationPath
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class ZipExtractor {

    fun extractIfNecessary(zipArchivePath: String, destinationPath: String) {
        createDirectory(destinationPath)
        extractZipArchive(zipArchivePath, destinationPath)
    }

    private fun createDirectory(destinationPath: String) {
        val directoryFile = File(destinationPath)
        if (directoryFile.exists()) {
            return
        }

        val directoryCreated = directoryFile.mkdirs()

        if (!directoryCreated) {
            throw IOException(Errors.CREATING_DIR + directoryFile.name)
        }
    }

    private fun extractZipArchive(zipArchivePath: String, destinationPath: String) {
        val zipInputStream = ZipInputStream(FileInputStream(zipArchivePath))

        var entry: ZipEntry? = zipInputStream.nextEntry

        while (entry != null) {
            val destinationFilePath = destinationPath + PATHS_SEPARATOR + entry.name
            extractToFile(zipInputStream, destinationFilePath)
            zipInputStream.closeEntry()

            entry = zipInputStream.nextEntry
        }
    }

    private fun extractToFile(zipInputStream: ZipInputStream, destinationFilePath: String) {
        BufferedOutputStream(FileOutputStream(destinationFilePath)).use { bufferedOutputStream ->
            val bytes = ByteArray(InputOutput.WRITING_BUFFER_SIZE)

            var read: Int
            
            while (zipInputStream.read(bytes).also { read = it } != -1) {
                bufferedOutputStream.write(bytes, 0, read)
            }
        }
    }

    fun extractIfNecessary(zipArchivePath: String, zippedFileName: String, destinationPath: String, namePrefixToAdd: String) {
        val fileDestinationPath = getFileDestinationPath(destinationPath, zippedFileName, namePrefixToAdd)
        val zippedFilePath = Paths.get(fileDestinationPath)

        if (!zippedFilePath.toFile().exists()) { // file not existent
            createDirectory(destinationPath)
            extractZipArchiveFile(zipArchivePath, zippedFileName, fileDestinationPath)
        } else {
            updateNecessaryFiles(zipArchivePath, zippedFileName, zippedFilePath, fileDestinationPath)
        }
    }

    private fun extractZipArchiveFile(zipArchivePath: String, fileToExtractName: String, destinationFilePath: String) {
        ZipInputStream(FileInputStream(zipArchivePath)).use { zipInputStream ->

            var entry: ZipEntry? = zipInputStream.nextEntry

            while (entry != null) {
                if (entry.name == fileToExtractName) {
                    extractToFile(zipInputStream, destinationFilePath)
                    zipInputStream.closeEntry()
                    return
                }
                zipInputStream.closeEntry()
                entry = zipInputStream.nextEntry
            }
        }

        throw FileNotFoundException(Errors.NOT_EXISTENT_FILE + fileToExtractName)
    }

    private fun updateNecessaryFiles(zipArchivePath: String, zippedFileName: String,
                                     filePathToCreate: Path, destinationFilePath: String) {
        val bytesFromZippedFile = getBytesFromZippedFile(zipArchivePath, zippedFileName)
        val bytesFromExtractedFile = Files.readAllBytes(filePathToCreate)

        // check bytes are equal
        if (!Arrays.equals(bytesFromExtractedFile, bytesFromZippedFile)) {
            Files.write(Paths.get(destinationFilePath), bytesFromZippedFile)
        }
    }

    private fun getBytesFromZippedFile(zipArchivePath: String, fileToExtractName: String): ByteArray {
        ZipInputStream(FileInputStream(zipArchivePath)).use { zipInputStream ->
            var entry: ZipEntry

            while (zipInputStream.nextEntry.also { entry = it } != null) {
                if (entry.name == fileToExtractName) {
                    val byteArray = getByteArray(zipInputStream)
                    zipInputStream.closeEntry()
                    return byteArray
                }
            }
        }

        throw FileNotFoundException(Errors.NOT_EXISTENT_FILE + fileToExtractName)
    }

    private fun getByteArray(zipInputStream: ZipInputStream): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val bytes = ByteArray(InputOutput.WRITING_BUFFER_SIZE)
        var read: Int

        while (zipInputStream.read(bytes).also { read = it } != -1) {
            byteArrayOutputStream.write(bytes, 0, read)
        }

        val readInBytes = byteArrayOutputStream.toByteArray()
        byteArrayOutputStream.close()
        return readInBytes
    }
}
