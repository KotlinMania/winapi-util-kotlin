// port-lint: source file.rs
package io.github.kotlinmania.winapiutil

internal actual fun getFileInformation(h: AsHandleRef): Information =
    throw UnsupportedOperationException("winapi-util file is only available on Windows")

internal actual fun getFileType(h: AsHandleRef): FileType =
    throw UnsupportedOperationException("winapi-util file is only available on Windows")
