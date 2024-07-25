package ru.avem.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import ru.avem.isTablet
import java.awt.Desktop
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Thread.sleep
import java.lang.reflect.Modifier
import java.nio.file.Paths
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.experimental.and

fun hash(value: String, algorithm: String = "SHA-256"): String {
    val bytes = value.toByteArray()
    val md = MessageDigest.getInstance(algorithm)
    val digest = md.digest(bytes)
    return digest.fold("", { str, it -> str + "%02x".format(it) })
}

fun copyFileFromStream(_inputStream: InputStream, dest: File) {
    _inputStream.use { inputStream ->
        try {
            val fileOutputStream = FileOutputStream(dest)
            val buffer = ByteArray(1024)
            var length = inputStream.read(buffer)
            while (length > 0) {
                fileOutputStream.write(buffer, 0, length)
                length = inputStream.read(buffer)
            }
        } catch (_: FileNotFoundException) {
        }
    }
}



fun getCurrentDate(): String = SimpleDateFormat("d MM yyyy").format(Date())
fun getCurrentTime(): String = SimpleDateFormat("HH:mm:ss").format(Date())

fun toHexStr(src: ByteArray): String {
    val builder = StringBuilder()
    for (element in src) {
        val s = Integer.toHexString((element and 0xFF.toByte()).toInt())
        if (s.length < 2) {
            builder.append(0)
        }
        builder.append(s).append(' ')
    }
    return builder.toString().toUpperCase().trim { it <= ' ' }
}

fun String.hexStrToAsciiStr(): String {
    val builder = StringBuilder()
    for (i in this.indices) {
        val s = this.substring(i, i + 1)

        if (s != " ")
            builder.append(s)
    }
    val output = StringBuilder("")
    if (builder.length > 1) {
        var i = 0
        while (i < builder.length) {
            val str = builder.substring(i, i + 2)
            output.append(Integer.parseInt(str.trim { it <= ' ' }, 16).toChar())
            i += 2
        }
    }
    return output.toString()
}

fun openKeyboard () {
    if (isTablet) {
        Desktop
            .getDesktop()
            .open(Paths.get("C:/Windows/system32/osk.exe").toFile())
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun androidx.compose.ui.Modifier.kb(): androidx.compose.ui.Modifier {
    return onPointerEvent(PointerEventType.Press) {
            if (isTablet) {
                Desktop
                    .getDesktop()
                    .open(Paths.get("C:/Windows/system32/osk.exe").toFile())
            }
    }
}


fun trimByteArray(bytes: ByteArray): ByteArray? {
    var i = bytes.size - 1
    while (i >= 0 && bytes[i].toInt() == 0) {
        --i
    }
    return bytes.copyOf(i + 1)
}

fun getAlignedString(input: String, columnWidth: Int, alignment: String = "LEFT"): String {
    var voidPlace = ""

    if (columnWidth > input.length) {
        for (i in 1..(columnWidth - input.length)) {
            voidPlace += " "
        }
    }

    return when (alignment) {
        "RIGHT" -> voidPlace + input
        else -> input + voidPlace
    }
}

private fun Int.padZero() = toString().padStart(2, '0')

fun smartSleep(
    mills: Long,
    breakCondition: () -> Boolean = { false },
    pauseCondition: () -> Boolean = { false }
) {
    if (mills > 0) {
        val initNanos = System.nanoTime()
        val stepMills = 10L
        val minusErrorNanos = 3_000_000L
        var iterations = mills / stepMills

        while (iterations >= 0L && !breakCondition()) {
            if (!pauseCondition()) {
                iterations--
            }

            sleep(stepMills - minusErrorNanos / 1_000_000)
        }

        val plusErrorNanos = (mills * 1_000_000 - (System.nanoTime() - initNanos)) - minusErrorNanos

        if (plusErrorNanos > 0L && !breakCondition()) {
            sleep(
                (plusErrorNanos / 1_000_000),
                (plusErrorNanos % 1_000_000).toInt()
            )
        }
    }
}
