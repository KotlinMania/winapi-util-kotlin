// port-lint: source src/win.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapiutil

import io.github.kotlinmania.windowssys.windows.win32.foundation.HANDLE
import io.github.kotlinmania.windowssys.windows.win32.foundation.INVALID_HANDLE_VALUE
import kotlinx.cinterop.toLong
import platform.windows.CreateFileW as winCreateFileW
import platform.windows.GetStdHandle as winGetStdHandle

internal const val STD_INPUT_HANDLE: UInt = 0xFFFFFFF6u
internal const val STD_OUTPUT_HANDLE: UInt = 0xFFFFFFF5u
internal const val STD_ERROR_HANDLE: UInt = 0xFFFFFFF4u

internal const val GENERIC_READ: UInt = 0x80000000u
internal const val FILE_SHARE_READ: UInt = 1u
internal const val OPEN_EXISTING: UInt = 3u
internal const val FILE_FLAG_BACKUP_SEMANTICS: UInt = 0x02000000u

// On Windows, open a raw HANDLE for read access from a file path.
internal actual fun openRawHandleForRead(path: String): HANDLE {
    val handle =
        winCreateFileW(
            path,
            GENERIC_READ,
            FILE_SHARE_READ,
            null,
            OPEN_EXISTING,
            0u,
            null,
        )
    if (handle?.toLong() == INVALID_HANDLE_VALUE) {
        throw RuntimeException("Failed to open file: $path")
    }
    return handle?.toLong() ?: 0L
}

internal actual fun openRawHandleForReadAny(path: String): HANDLE {
    val handle =
        winCreateFileW(
            path,
            GENERIC_READ,
            FILE_SHARE_READ,
            null,
            OPEN_EXISTING,
            FILE_FLAG_BACKUP_SEMANTICS,
            null,
        )
    if (handle?.toLong() == INVALID_HANDLE_VALUE) {
        throw RuntimeException("Failed to open file: $path")
    }
    return handle?.toLong() ?: 0L
}

internal actual fun getRawHandleFromFile(file: Any): HANDLE =
    throw UnsupportedOperationException("Use fromRawHandle on Kotlin/Native")

internal actual fun stdinRawHandle(): HANDLE =
    winGetStdHandle(STD_INPUT_HANDLE)?.toLong() ?: 0L

internal actual fun stdoutRawHandle(): HANDLE =
    winGetStdHandle(STD_OUTPUT_HANDLE)?.toLong() ?: 0L

internal actual fun stderrRawHandle(): HANDLE =
    winGetStdHandle(STD_ERROR_HANDLE)?.toLong() ?: 0L
