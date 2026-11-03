// port-lint: source file.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapiutil

import io.github.kotlinmania.windowssys.windows.win32.foundation.FILETIME
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toCPointer
import platform.windows._BY_HANDLE_FILE_INFORMATION
import platform.windows.GetFileInformationByHandle as winGetFileInformationByHandle
import platform.windows.GetFileType as winGetFileType

internal actual fun getFileInformation(h: AsHandleRef): Information {
    memScoped {
        val info = alloc<_BY_HANDLE_FILE_INFORMATION>()
        val rc = winGetFileInformationByHandle(h.asRaw().toCPointer(), info.ptr)
        if (rc == 0) {
            throw RuntimeException("GetFileInformationByHandle failed")
        }
        return Information(
            fileAttributesValue = info.dwFileAttributes,
            creationTime =
                FILETIME(
                    dwLowDateTime = info.ftCreationTime.dwLowDateTime,
                    dwHighDateTime = info.ftCreationTime.dwHighDateTime,
                ),
            lastAccessTime =
                FILETIME(
                    dwLowDateTime = info.ftLastAccessTime.dwLowDateTime,
                    dwHighDateTime = info.ftLastAccessTime.dwHighDateTime,
                ),
            lastWriteTime =
                FILETIME(
                    dwLowDateTime = info.ftLastWriteTime.dwLowDateTime,
                    dwHighDateTime = info.ftLastWriteTime.dwHighDateTime,
                ),
            volumeSerialNumberValue = info.dwVolumeSerialNumber,
            fileSizeHigh = info.nFileSizeHigh,
            fileSizeLow = info.nFileSizeLow,
            numberOfLinksValue = info.nNumberOfLinks,
            fileIndexHigh = info.nFileIndexHigh,
            fileIndexLow = info.nFileIndexLow,
        )
    }
}

internal actual fun getFileType(h: AsHandleRef): FileType {
    val rc = winGetFileType(h.asRaw().toCPointer())
    return FileType(rc)
}
