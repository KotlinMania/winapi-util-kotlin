// port-lint: source src/win.rs
// Non-Windows stub actuals. The upstream crate is completely empty
// on non-Windows platforms (`#[cfg(windows)]` guards). These actuals
// satisfy the expect declarations so the KMP project compiles on
// every target, but they throw because the underlying Win32 APIs do
// not exist outside Windows.
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
