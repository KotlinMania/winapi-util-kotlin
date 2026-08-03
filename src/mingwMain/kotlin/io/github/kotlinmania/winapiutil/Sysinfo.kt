// port-lint: source src/sysinfo.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapiutil

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.windows.GetComputerNameExW
import platform.windows.WCHARVar
import platform.windows._COMPUTER_NAME_FORMAT

// Mapping from our public enum to the Windows SDK COMPUTER_NAME_FORMAT
// enumeration values, accessed via the cinterop-generated companion
// object constants on _COMPUTER_NAME_FORMAT.
private fun ComputerNameKind.toFormat(): _COMPUTER_NAME_FORMAT =
    when (this) {
        ComputerNameKind.DnsDomain -> _COMPUTER_NAME_FORMAT.ComputerNameDnsDomain
        ComputerNameKind.DnsFullyQualified -> _COMPUTER_NAME_FORMAT.ComputerNameDnsFullyQualified
        ComputerNameKind.DnsHostname -> _COMPUTER_NAME_FORMAT.ComputerNameDnsHostname
        ComputerNameKind.NetBios -> _COMPUTER_NAME_FORMAT.ComputerNameNetBIOS
        ComputerNameKind.PhysicalDnsDomain -> _COMPUTER_NAME_FORMAT.ComputerNamePhysicalDnsDomain
        ComputerNameKind.PhysicalDnsFullyQualified -> _COMPUTER_NAME_FORMAT.ComputerNamePhysicalDnsFullyQualified
        ComputerNameKind.PhysicalDnsHostname -> _COMPUTER_NAME_FORMAT.ComputerNamePhysicalDnsHostname
        ComputerNameKind.PhysicalNetBios -> _COMPUTER_NAME_FORMAT.ComputerNamePhysicalNetBIOS
    }

public actual fun getComputerName(kind: ComputerNameKind): String =
    memScoped {
        val format = kind.toFormat()
        val lenVar = alloc<UIntVar>().apply { value = 0u }
        // First call with null buffer to get the required size.
        GetComputerNameExW(format, null, lenVar.ptr)
        val len = lenVar.value.toInt()

        val buf = allocArray<WCHARVar>(len)
        val rc = GetComputerNameExW(format, buf, lenVar.ptr)
        if (rc == 0) {
            throw RuntimeException("GetComputerNameExW failed")
        }
        val total = lenVar.value.toInt()
        val chars = CharArray(total) { i -> buf[i].toInt().toChar() }
        chars.concatToString()
    }
