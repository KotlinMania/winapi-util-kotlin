// port-lint: source src/sysinfo.rs
package io.github.kotlinmania.winapiutil

// The type of name to be retrieved by [getComputerName].
public enum class ComputerNameKind {
    // The name of the DNS domain assigned to the local computer.
    DnsDomain,

    // The fully qualified DNS name that uniquely identifies the local
    // computer.
    DnsFullyQualified,

    // The DNS host name of the local computer.
    DnsHostname,

    // The NetBIOS name of the local computer.
    NetBios,

    // The name of the DNS domain assigned to the local computer
    // (physical).
    PhysicalDnsDomain,

    // The fully qualified DNS name that uniquely identifies the
    // computer (physical).
    PhysicalDnsFullyQualified,

    // The DNS host name of the local computer (physical).
    PhysicalDnsHostname,

    // The NetBIOS name of the local computer (physical).
    PhysicalNetBios,
}

// Retrieves a NetBIOS or DNS name associated with the local computer.
//
// The names are established at system startup, when the system reads
// them from the registry.
public expect fun getComputerName(kind: ComputerNameKind): String
