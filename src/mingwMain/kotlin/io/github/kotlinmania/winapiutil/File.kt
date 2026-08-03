// port-lint: source src/file.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapiutil

import io.github.kotlinmania.windowssys.windows.win32.foundation.FILETIME
import kotlinx.cinterop.cValue
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.usePinned
import io.github.kotlinmania.winapiutil.cinterop.GetFileInformationByHandle as winGetFileInformationByHandle
import io.github.kotlinmania.winapiutil.cinterop.GetFileType as winGetFileType

public actual fun information(h: AsHandleRef): Information {
    val info = cValue<BY_HANDLE_FILE_INFORMATION>()
    val rc = winGetFileInformationByHandle(h.asRaw().toCPointer(), info.ptr)
    if (rc == 0) {
        throw RuntimeException("GetFileInformationByHandle failed")
    }
    info.usePinned { pinned ->
        return Information(
            fileAttributesValue = pinned.dwFileAttributes,
            creationTime =
                FILETIME(
                    dwLowDateTime = pinned.ftCreationTime.dwLowDateTime,
                    dwHighDateTime = pinned.ftCreationTime.dwHighDateTime,
                ),
            lastAccessTime =
                FILETIME(
                    dwLowDateTime = pinned.ftLastAccessTime.dwLowDateTime,
                    dwHighDateTime = pinned.ftLastAccessTime.dwHighDateTime,
                ),
            lastWriteTime =
                FILETIME(
                    dwLowDateTime = pinned.ftLastWriteTime.dwLowDateTime,
                    dwHighDateTime = pinned.ftLastWriteTime.dwHighDateTime,
                ),
            volumeSerialNumberValue = pinned.dwVolumeSerialNumber,
            fileSizeHigh = pinned.nFileSizeHigh,
            fileSizeLow = pinned.nFileSizeLow,
            numberOfLinksValue = pinned.nNumberOfLinks,
            fileIndexHigh = pinned.nFileIndexHigh,
            fileIndexLow = pinned.nFileIndexLow,
        )
    }
}

public actual fun typ(h: AsHandleRef): FileType {
    val rc = winGetFileType(h.asRaw().toCPointer())
    return FileType(rc)
}
