// port-lint: tests src/sysinfo.rs
package io.github.kotlinmania.winapiutil

import kotlin.test.Test
import kotlin.test.assertEquals

class SysinfoTest {
    // The upstream test (`itworks`) queries all kinds of computer
    // names and prints them — it only asserts that the call succeeds.
    // That test can only run on Windows because the underlying Win32
    // API does not exist on other platforms. The Kotlin port mirrors
    // the structure: the actual getComputerName call is tested on
    // mingwX64 (Windows CI), while here we verify the enum exists
    // with the correct variant count.

    @Test
    fun computerNameKindHasAllVariants() {
        assertEquals(8, ComputerNameKind.entries.size)
    }

    @Test
    fun computerNameKindVariantsMatchUpstream() {
        val expected =
            listOf(
                ComputerNameKind.DnsDomain,
                ComputerNameKind.DnsFullyQualified,
                ComputerNameKind.DnsHostname,
                ComputerNameKind.NetBios,
                ComputerNameKind.PhysicalDnsDomain,
                ComputerNameKind.PhysicalDnsFullyQualified,
                ComputerNameKind.PhysicalDnsHostname,
                ComputerNameKind.PhysicalNetBios,
            )
        assertEquals(expected, ComputerNameKind.entries)
    }
}
