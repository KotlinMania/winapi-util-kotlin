// port-lint: tests sysinfo.rs
package io.github.kotlinmania.winapiutil

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class WinapiWindowsTest {
    @Test
    fun win32GetComputerNameAllKinds() {
        for (kind in ComputerNameKind.entries) {
            val value = getComputerName(kind)
            assertNotNull(value)
        }
        assertTrue(getComputerName(ComputerNameKind.NetBios).isNotEmpty())
        assertTrue(getComputerName(ComputerNameKind.DnsHostname).isNotEmpty())
        assertTrue(getComputerName(ComputerNameKind.PhysicalNetBios).isNotEmpty())
        assertTrue(getComputerName(ComputerNameKind.PhysicalDnsHostname).isNotEmpty())
    }

    @Test
    fun win32OpenDirectoryInformationTypeAndClose() {
        val handle = Handle.fromPathAny("C:\\Windows")
        try {
            val info = information(handle)
            assertTrue(info.fileAttributes() > 0uL)
            assertTrue(typ(handle).isDisk())
        } finally {
            handle.close()
        }
    }

    @Test
    fun win32StdHandlesAcquireCleanly() {
        val stdinRef = HandleRef.stdin()
        val stdoutRef = HandleRef.stdout()
        val stderrRef = HandleRef.stderr()
        assertTrue(stdinRef.asRaw() == stdinRef.asRawHandle())
        assertTrue(stdoutRef.asRaw() == stdoutRef.asRawHandle())
        assertTrue(stderrRef.asRaw() == stderrRef.asRawHandle())
    }
}
