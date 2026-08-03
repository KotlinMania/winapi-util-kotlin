// port-lint: source src/sysinfo.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapiutil

import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toKStringFromUtf16
import kotlinx.cinterop.value
import io.github.kotlinmania.winapiutil.cinterop.GetComputerNameExW as winGetComputerNameExW

// COMPUTER_NAME_FORMAT enumeration values from the Windows SDK.
private const val ComputerNameDnsDomain: UInt = 1u
private const val ComputerNameDnsFullyQualified: UInt = 2u
private const val ComputerNameDnsHostname: UInt = 3u
private const val ComputerNameNetBIOS: UInt = 0u
private const val ComputerNamePhysicalDnsDomain: UInt = 5u
private const val ComputerNamePhysicalDnsFullyQualified: UInt = 6u
private const val ComputerNamePhysicalDnsHostname: UInt = 4u
private const val ComputerNamePhysicalNetBIOS: UInt = 7u

private fun ComputerNameKind.toFormat(): UInt =
    when (this) {
        ComputerNameKind.DnsDomain -> ComputerNameDnsDomain
        ComputerNameKind.DnsFullyQualified -> ComputerNameDnsFullyQualified
        ComputerNameKind.DnsHostname -> ComputerNameDnsHostname
        ComputerNameKind.NetBios -> ComputerNameNetBIOS
        ComputerNameKind.PhysicalDnsDomain -> ComputerNamePhysicalDnsDomain
        ComputerNameKind.PhysicalDnsFullyQualified -> ComputerNamePhysicalDnsFullyQualified
        ComputerNameKind.PhysicalDnsHostname -> ComputerNamePhysicalDnsHostname
        ComputerNameKind.PhysicalNetBios -> ComputerNamePhysicalNetBIOS
    }

public actual fun getComputerName(kind: ComputerNameKind): String =
    memScoped {
        val format = kind.toFormat()
        val lenVar = UIntVar(0u)
        // First call with null buffer to get the required size.
        winGetComputerNameExW(format, null, lenVar.ptr)
        val len = lenVar.value.toInt()

        val buf = allocArray<UShortVar>(len)
        val rc = winGetComputerNameExW(format, buf.ptr, lenVar.ptr)
        if (rc == 0) {
            throw RuntimeException("GetComputerNameExW failed")
        }
        buf.toKStringFromUtf16()
    }
