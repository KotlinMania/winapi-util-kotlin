// port-lint: source src/win.rs
package io.github.kotlinmania.winapiutil

import io.github.kotlinmania.windowssys.windows.win32.foundation.HANDLE

internal actual fun openRawHandleForRead(path: String): HANDLE =
    throw UnsupportedOperationException("winapi-util is only available on Windows")

internal actual fun openRawHandleForReadAny(path: String): HANDLE =
    throw UnsupportedOperationException("winapi-util is only available on Windows")

internal actual fun getRawHandleFromFile(file: Any): HANDLE =
    throw UnsupportedOperationException("winapi-util is only available on Windows")

internal actual fun stdinRawHandle(): HANDLE =
    throw UnsupportedOperationException("winapi-util is only available on Windows")

internal actual fun stdoutRawHandle(): HANDLE =
    throw UnsupportedOperationException("winapi-util is only available on Windows")

internal actual fun stderrRawHandle(): HANDLE =
    throw UnsupportedOperationException("winapi-util is only available on Windows")
