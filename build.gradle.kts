import java.io.*
import java.net.URL
import java.util.zip.GZIPInputStream
import java.util.zip.ZipInputStream

plugins {
    id("java")
}

group = "ru.medo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {}

val jBoxHomeBin = File(System.getProperty("user.home"), ".jbox/bin")
val os = if (System.getProperty("os.name").lowercase().contains("win")) "windows" else "linux"

val singBoxUrl = if (os == "windows") {
    "https://github.com/SagerNet/sing-box/releases/download/v1.11.11/sing-box-1.11.11-windows-amd64.zip"
} else {
    "https://github.com/SagerNet/sing-box/releases/download/v1.11.11/sing-box-1.11.11-linux-amd64.tar.gz"
}

tasks.register("downloadSingBox") {
    doLast {
        jBoxHomeBin.mkdirs()

        val tempFile = File.createTempFile("sing-box", if (os == "windows") ".zip" else ".tar.gz")
        tempFile.outputStream().use { out ->
            URL(singBoxUrl).openStream().use { input ->
                input.copyTo(out)
            }
        }

        if (os == "windows") {
            ZipInputStream(tempFile.inputStream()).use { zip ->
                var entry = zip.nextEntry
                while (entry != null) {
                    if (!entry.isDirectory && entry.name.endsWith("sing-box.exe")) {
                        val targetFile = File(jBoxHomeBin, "sing-box.exe")
                        targetFile.outputStream().use { zip.copyTo(it) }
                        targetFile.setExecutable(true)
                        break
                    }
                    entry = zip.nextEntry
                }
            }
        } else {
            GZIPInputStream(tempFile.inputStream()).use { gzip ->
                val input = BufferedInputStream(gzip)

                while (true) {
                    val header = ByteArray(512)
                    if (input.read(header) != 512) break

                    val name = header.takeWhile { it != 0.toByte() }.toByteArray().toString(Charsets.US_ASCII).trim('\u0000')
                    if (name.isEmpty()) break

                    val sizeOctal = header.copyOfRange(124, 136)
                        .toString(Charsets.US_ASCII)
                        .trim().removeSuffix("\u0000")
                    val size = sizeOctal.toLongOrNull(8) ?: break

                    if (name.endsWith("/sing-box")) {
                        val outputFile = File(jBoxHomeBin, "sing-box")
                        outputFile.outputStream().use { out ->
                            var remaining = size
                            val buffer = ByteArray(8192)
                            while (remaining > 0) {
                                val read = input.read(buffer, 0, minOf(buffer.size.toLong(), remaining).toInt())
                                if (read == -1) break
                                out.write(buffer, 0, read)
                                remaining -= read
                            }
                        }
                        File(jBoxHomeBin, "sing-box").setExecutable(true)
                        break
                    } else {
                        // Пропустить текущий файл
                        var skip = size
                        if (skip % 512 != 0L) skip += 512 - (skip % 512)
                        input.skip(skip)
                    }
                }
            }
        }

        tempFile.delete()
        println("sing-box downloaded to: ${jBoxHomeBin.absolutePath}")
    }
}
