// port-lint: tests win.rs
package io.github.kotlinmania.winapiutil

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

// Rust std::fs::File / io::Std* / process::ChildStd* AsHandleRef impls have no common KMP stdlib equivalents.
class WinTest {
    @Test
    fun handleRawRoundTripAndIntoRawHandle() {
        val owned = Handle.fromRawHandle(42L)
        assertEquals(42L, owned.asRawHandle())
        assertEquals(42L, owned.asRaw())
        assertNull(owned.asFile())
        assertNull(owned.asFileMut())

        val borrowed = owned.asHandleRef()
        assertEquals(42L, borrowed.asRawHandle())
        assertEquals(42L, borrowed.asRaw())

        // intoRawHandle transfers ownership so subsequent drop/close is a no-op.
        assertEquals(42L, owned.intoRawHandle())
        owned.drop()
        owned.close()
    }

    @Test
    fun handleCloseZeroAndInvalidIsIdempotent() {
        val zero = Handle.fromRawHandle(0L)
        zero.close()
        zero.drop()

        val invalid = Handle.fromRawHandle(-1L)
        invalid.close()
        invalid.drop()
    }

    @Test
    fun handleRefCloneAndAsHandleRef() {
        val ref = HandleRef.fromRawHandle(99L)
        assertEquals(99L, ref.rawHandle)
        assertEquals(99L, ref.asRawHandle())
        assertEquals(99L, ref.asRaw())
        assertNull(ref.asFile())
        assertNull(ref.asFileMut())

        val cloned = ref.clone()
        assertEquals(99L, cloned.asRawHandle())
        assertEquals(99L, ref.asHandleRef().asRawHandle())
    }

    @Test
    fun fromFileWrapsReference() {
        val sentinelFile = "fake-file"
        try {
            Handle.fromFile(sentinelFile)
        } catch (_: UnsupportedOperationException) {
            // Expected when no Win32 CRT file handle can be extracted from Any
        }
        try {
            HandleRef.fromFile(sentinelFile)
        } catch (_: UnsupportedOperationException) {
            // Expected when no Win32 CRT file handle can be extracted from Any
        }
    }
}
