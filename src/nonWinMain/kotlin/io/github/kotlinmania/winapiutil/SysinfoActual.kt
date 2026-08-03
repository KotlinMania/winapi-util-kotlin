// port-lint: source src/sysinfo.rs
// Non-Windows stub actuals for system information FFI functions.
package io.github.kotlinmania.winapiutil

public actual fun getComputerName(kind: ComputerNameKind): String =
    throw UnsupportedOperationException("winapi-util sysinfo is only available on Windows")
