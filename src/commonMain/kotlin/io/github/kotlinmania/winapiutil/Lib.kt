// port-lint: source lib.rs
package io.github.kotlinmania.winapiutil

/**
 * This library provides safe routines for interacting with Windows system APIs.
 *
 * A key abstraction is the combination of the [Handle] and [HandleRef] types.
 * Both represent a valid Windows handle to an I/O-like object, where [Handle]
 * is owned (the resource is closed when the handle is closed) and [HandleRef]
 * is borrowed (the resource is not closed when the handle is dropped).
 *
 * Routines in this library work on handles and accept anything that can be
 * converted into an [AsHandleRef].
 *
 * On non-Windows platforms, safe fallback implementations are provided so
 * multiplatform code can compile and run cleanly across all targets.
 */
public object WinapiUtil {
    public const val MODULE_CONSOLE: String = "console"
    public const val MODULE_FILE: String = "file"
    public const val MODULE_SYSINFO: String = "sysinfo"
}
