// port-lint: source win.rs
package io.github.kotlinmania.winapiutil

import io.github.kotlinmania.windowssys.windows.win32.foundation.HANDLE

// Expect declarations for platform-specific handle acquisition.
// Real implementations exist only on mingwX64 (Windows). On all
// other targets these functions throw because the underlying Win32
// APIs do not exist.

internal expect fun openRawHandleForRead(path: String): HANDLE

internal expect fun openRawHandleForReadAny(path: String): HANDLE

internal expect fun getRawHandleFromFile(file: Any): HANDLE

internal expect fun stdinRawHandle(): HANDLE

internal expect fun stdoutRawHandle(): HANDLE

internal expect fun stderrRawHandle(): HANDLE
