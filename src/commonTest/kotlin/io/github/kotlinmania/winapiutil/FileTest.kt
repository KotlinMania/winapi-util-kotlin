// port-lint: tests src/file.rs
package io.github.kotlinmania.winapiutil

import io.github.kotlinmania.windowssys.windows.win32.foundation.FILETIME
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FileTest {
    @Test
    fun isHiddenReturnsTrueWhenHiddenBitSet() {
        assertTrue(isHidden(FILE_ATTRIBUTE_HIDDEN))
    }

    @Test
    fun isHiddenReturnsFalseWhenHiddenBitNotSet() {
        assertFalse(isHidden(0uL))
        assertFalse(isHidden(1uL))
        assertFalse(isHidden(0xFFFFFFFDuL))
    }

    @Test
    fun isHiddenReturnsTrueWhenHiddenBitAmongOthers() {
        assertTrue(isHidden(FILE_ATTRIBUTE_HIDDEN or 0x80uL))
    }

    @Test
    fun filetimeToU64ReturnsNullForZero() {
        val ft = FILETIME(dwLowDateTime = 0u, dwHighDateTime = 0u)
        assertNull(filetimeToU64(ft))
    }

    @Test
    fun filetimeToU64CombinesHighAndLow() {
        val ft = FILETIME(dwLowDateTime = 0x12345678u, dwHighDateTime = 0x9ABCDEF0u)
        val expected = (0x9ABCDEF0uL shl 32) or 0x12345678uL
        assertEquals(expected, filetimeToU64(ft))
    }

    @Test
    fun fileSizeCombinesHighAndLow() {
        val info =
            Information(
                fileAttributesValue = 0u,
                creationTime = FILETIME(),
                lastAccessTime = FILETIME(),
                lastWriteTime = FILETIME(),
                volumeSerialNumberValue = 0u,
                fileSizeHigh = 0x00000001u,
                fileSizeLow = 0x00000002u,
                numberOfLinksValue = 1u,
                fileIndexHigh = 0u,
                fileIndexLow = 0u,
            )
        assertEquals((1uL shl 32) or 2uL, info.fileSize())
    }

    @Test
    fun fileIndexCombinesHighAndLow() {
        val info =
            Information(
                fileAttributesValue = 0u,
                creationTime = FILETIME(),
                lastAccessTime = FILETIME(),
                lastWriteTime = FILETIME(),
                volumeSerialNumberValue = 0u,
                fileSizeHigh = 0u,
                fileSizeLow = 0u,
                numberOfLinksValue = 1u,
                fileIndexHigh = 0xABCDu,
                fileIndexLow = 0x1234u,
            )
        assertEquals((0xABCDuL shl 32) or 0x1234uL, info.fileIndex())
    }

    @Test
    fun typeIsCharReturnsTrueForCharType() {
        assertEquals(true, Type(FILE_TYPE_CHAR).isChar())
        assertEquals(false, Type(FILE_TYPE_CHAR).isDisk())
    }

    @Test
    fun typeIsDiskReturnsTrueForDiskType() {
        assertEquals(true, Type(FILE_TYPE_DISK).isDisk())
        assertEquals(false, Type(FILE_TYPE_DISK).isPipe())
    }

    @Test
    fun typeIsPipeReturnsTrueForPipeType() {
        assertEquals(true, Type(FILE_TYPE_PIPE).isPipe())
        assertEquals(false, Type(FILE_TYPE_PIPE).isUnknown())
    }

    @Test
    fun typeIsUnknownReturnsTrueForUnknownType() {
        assertEquals(true, Type(FILE_TYPE_UNKNOWN).isUnknown())
        assertEquals(false, Type(FILE_TYPE_UNKNOWN).isChar())
    }
}
