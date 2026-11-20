// port-lint: tests sysinfo.rs
package io.github.kotlinmania.winapiutil

import kotlin.test.Test
import kotlin.test.assertEquals

class SysinfoTest {
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

    @Test
    fun computerNameKindToFormatMatchesWin32Values() {
        assertEquals(0, ComputerNameKind.NetBios.toFormat())
        assertEquals(1, ComputerNameKind.DnsHostname.toFormat())
        assertEquals(2, ComputerNameKind.DnsDomain.toFormat())
        assertEquals(3, ComputerNameKind.DnsFullyQualified.toFormat())
        assertEquals(4, ComputerNameKind.PhysicalNetBios.toFormat())
        assertEquals(5, ComputerNameKind.PhysicalDnsHostname.toFormat())
        assertEquals(6, ComputerNameKind.PhysicalDnsDomain.toFormat())
        assertEquals(7, ComputerNameKind.PhysicalDnsFullyQualified.toFormat())
    }

    @Test
    fun itworks() {
        val kinds =
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
        for (kind in kinds) {
            try {
                val name = getComputerName(kind)
                println("$kind: $name")
            } catch (_: UnsupportedOperationException) {
                // Expected on non-Windows host platforms
            }
        }
    }
}
