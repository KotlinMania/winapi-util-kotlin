// port-lint: source src/file.rs
package io.github.kotlinmania.winapiutil

import io.github.kotlinmania.windowssys.windows.win32.foundation.FILETIME

// Returns true if and only if the given file attributes contain the
// FILE_ATTRIBUTE_HIDDEN attribute.
public fun isHidden(fileAttributes: ULong): Boolean =
    (fileAttributes and FILE_ATTRIBUTE_HIDDEN) > 0uL

// Represents file information such as creation time, file size, etc.
//
// This wraps a BY_HANDLE_FILE_INFORMATION.
public class Information internal constructor(
    internal val fileAttributesValue: UInt,
    internal val creationTime: FILETIME,
    internal val lastAccessTime: FILETIME,
    internal val lastWriteTime: FILETIME,
    internal val volumeSerialNumberValue: UInt,
    internal val fileSizeHigh: UInt,
    internal val fileSizeLow: UInt,
    internal val numberOfLinksValue: UInt,
    internal val fileIndexHigh: UInt,
    internal val fileIndexLow: UInt,
) {
    // Returns file attributes.
    public fun fileAttributes(): ULong = fileAttributesValue.toULong()

    // Returns true if and only if this file information has the
    // FILE_ATTRIBUTE_HIDDEN attribute.
    public fun isHidden(): Boolean = isHidden(fileAttributes())

    // Return the creation time, if one exists.
    public fun creationTime(): ULong? = filetimeToU64(creationTime)

    // Return the last access time, if one exists.
    public fun lastAccessTime(): ULong? = filetimeToU64(lastAccessTime)

    // Return the last write time, if one exists.
    public fun lastWriteTime(): ULong? = filetimeToU64(lastWriteTime)

    // Return the serial number of the volume that the file is on.
    public fun volumeSerialNumber(): ULong = volumeSerialNumberValue.toULong()

    // Return the file size, in bytes.
    public fun fileSize(): ULong =
        (fileSizeHigh.toULong() shl 32) or fileSizeLow.toULong()

    // Return the number of links to this file.
    public fun numberOfLinks(): ULong = numberOfLinksValue.toULong()

    // Return the index of this file. The index of a file is a
    // purportedly unique identifier for a file within a particular
    // volume.
    public fun fileIndex(): ULong =
        (fileIndexHigh.toULong() shl 32) or fileIndexLow.toULong()
}

// Represents a Windows file type.
//
// This wraps the result of GetFileType.
public class Type internal constructor(
    internal val rawValue: UInt,
) {
    // Returns true if this type represents a character file, which is
    // typically an LPT device or a console.
    public fun isChar(): Boolean = rawValue == FILE_TYPE_CHAR

    // Returns true if this type represents a disk file.
    public fun isDisk(): Boolean = rawValue == FILE_TYPE_DISK

    // Returns true if this type represents a socket, named pipe or an
    // anonymous pipe.
    public fun isPipe(): Boolean = rawValue == FILE_TYPE_PIPE

    // Returns true if this type is not known.
    //
    // Note that this never corresponds to a failure.
    public fun isUnknown(): Boolean = rawValue == FILE_TYPE_UNKNOWN
}

// FILE_TYPE_CHAR   = 0x0002
// FILE_TYPE_DISK   = 0x0001
// FILE_TYPE_PIPE   = 0x0003
// FILE_TYPE_UNKNOWN = 0x0000
internal const val FILE_TYPE_CHAR: UInt = 2u
internal const val FILE_TYPE_DISK: UInt = 1u
internal const val FILE_TYPE_PIPE: UInt = 3u
internal const val FILE_TYPE_UNKNOWN: UInt = 0u

// Return various pieces of information about a file.
//
// This includes information such as a file's size, unique identifier
// and time related fields.
public expect fun information(h: AsHandleRef): Information

// Returns the file type of the given handle.
public expect fun typ(h: AsHandleRef): Type

internal fun filetimeToU64(t: FILETIME): ULong? {
    val v = (t.dwHighDateTime.toULong() shl 32) or t.dwLowDateTime.toULong()
    return if (v == 0uL) null else v
}
