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

    public fun toFormat(): Int =
        when (this) {
            DnsDomain -> COMPUTER_NAME_DNS_DOMAIN
            DnsFullyQualified -> COMPUTER_NAME_DNS_FULLY_QUALIFIED
            DnsHostname -> COMPUTER_NAME_DNS_HOSTNAME
            NetBios -> COMPUTER_NAME_NET_BIOS
            PhysicalDnsDomain -> COMPUTER_NAME_PHYSICAL_DNS_DOMAIN
            PhysicalDnsFullyQualified -> COMPUTER_NAME_PHYSICAL_DNS_FULLY_QUALIFIED
            PhysicalDnsHostname -> COMPUTER_NAME_PHYSICAL_DNS_HOSTNAME
            PhysicalNetBios -> COMPUTER_NAME_PHYSICAL_NET_BIOS
        }
}

internal const val COMPUTER_NAME_NET_BIOS: Int = 0
internal const val COMPUTER_NAME_DNS_HOSTNAME: Int = 1
internal const val COMPUTER_NAME_DNS_DOMAIN: Int = 2
internal const val COMPUTER_NAME_DNS_FULLY_QUALIFIED: Int = 3
internal const val COMPUTER_NAME_PHYSICAL_NET_BIOS: Int = 4
internal const val COMPUTER_NAME_PHYSICAL_DNS_HOSTNAME: Int = 5
internal const val COMPUTER_NAME_PHYSICAL_DNS_DOMAIN: Int = 6
internal const val COMPUTER_NAME_PHYSICAL_DNS_FULLY_QUALIFIED: Int = 7

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
