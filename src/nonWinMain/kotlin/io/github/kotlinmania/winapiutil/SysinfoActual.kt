// port-lint: source src/sysinfo.rs
package io.github.kotlinmania.winapiutil

internal actual fun getComputerNamePlatform(kind: ComputerNameKind): String =
    throw UnsupportedOperationException("winapi-util sysinfo is only available on Windows")
