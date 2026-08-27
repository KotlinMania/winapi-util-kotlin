// port-lint: source sysinfo.rs
package io.github.kotlinmania.winapiutil

/**
 * The type of name to be retrieved by [getComputerName].
 */
public enum class ComputerNameKind {
    /**
     * The name of the DNS domain assigned to the local computer. If the local
     * computer is a node in a cluster, lpBuffer receives the DNS domain name
     * of the cluster virtual server.
     */
    DnsDomain,

    /**
     * The fully qualified DNS name that uniquely identifies the local
     * computer. This name is a combination of the DNS host name and the DNS
     * domain name, using the form HostName.DomainName. If the local computer
     * is a node in a cluster, lpBuffer receives the fully qualified DNS name
     * of the cluster virtual server.
     */
    DnsFullyQualified,

    /**
     * The DNS host name of the local computer. If the local computer is a
     * node in a cluster, lpBuffer receives the DNS host name of the cluster
     * virtual server.
     */
    DnsHostname,

    /**
     * The NetBIOS name of the local computer. If the local computer is a node
     * in a cluster, lpBuffer receives the NetBIOS name of the cluster virtual
     * server.
     */
    NetBios,

    /**
     * The name of the DNS domain assigned to the local computer. If the local
     * computer is a node in a cluster, lpBuffer receives the DNS domain name
     * of the local computer, not the name of the cluster virtual server.
     */
    PhysicalDnsDomain,

    /**
     * The fully qualified DNS name that uniquely identifies the computer. If
     * the local computer is a node in a cluster, lpBuffer receives the fully
     * qualified DNS name of the local computer, not the name of the cluster
     * virtual server.
     *
     * The fully qualified DNS name is a combination of the DNS host name and
     * the DNS domain name, using the form HostName.DomainName.
     */
    PhysicalDnsFullyQualified,

    /**
     * The DNS host name of the local computer. If the local computer is a
     * node in a cluster, lpBuffer receives the DNS host name of the local
     * computer, not the name of the cluster virtual server.
     */
    PhysicalDnsHostname,

    /**
     * The NetBIOS name of the local computer. If the local computer is a node
     * in a cluster, lpBuffer receives the NetBIOS name of the local computer,
     * not the name of the cluster virtual server.
     */
    PhysicalNetBios,
    ;

    public fun toFormat(): Int = ordinal
}

/**
 * Retrieves a NetBIOS or DNS name associated with the local computer.
 *
 * The names are established at system startup, when the system reads them
 * from the registry.
 *
 * This corresponds to calling `GetComputerNameExW`.
 */
public fun getComputerName(kind: ComputerNameKind): String = getComputerNamePlatform(kind)

internal expect fun getComputerNamePlatform(kind: ComputerNameKind): String
