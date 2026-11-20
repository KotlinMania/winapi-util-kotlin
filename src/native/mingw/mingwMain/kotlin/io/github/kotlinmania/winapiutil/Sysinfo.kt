// port-lint: source sysinfo.rs
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
private fun ComputerNameKind.toNativeFormat(): _COMPUTER_NAME_FORMAT =
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

internal actual fun getComputerNamePlatform(kind: ComputerNameKind): String =
    memScoped {
        val format = kind.toNativeFormat()
        val len1Var = alloc<UIntVar>().apply { value = 0u }
        // First call with null pointer writes the required buffer size (including NUL) to len1.
        GetComputerNameExW(format, null, len1Var.ptr)
        val len1 = len1Var.value

        val buf = allocArray<WCHARVar>(len1.toInt())
        val len2Var = alloc<UIntVar>().apply { value = len1 }
        val rc = GetComputerNameExW(format, buf, len2Var.ptr)
        if (rc == 0) {
            throw RuntimeException("GetComputerNameExW failed")
        }
        val len2 = len2Var.value
        if (len1 <= len2) {
            throw RuntimeException(
                "GetComputerNameExW buffer length mismatch, " +
                    "expected length strictly less than $len1 but got $len2",
            )
        }
        val chars = CharArray(len2.toInt()) { i -> Char(buf[i].toInt()) }
        chars.concatToString()
    }
