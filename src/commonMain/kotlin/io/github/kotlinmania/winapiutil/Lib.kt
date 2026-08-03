// port-lint: source src/lib.rs
package io.github.kotlinmania.winapiutil

// Upstream `src/lib.rs` is a crate-level module dispatcher: it
// re-exports `win::*` and declares `console`, `file`, `sysinfo`, and
// the private `win` module — all behind `#[cfg(windows)]`. On
// non-Windows the crate is completely empty.
//
// In the Kotlin port, `Win.kt` carries the `Handle` / `HandleRef` /
// `AsHandleRef` types, `Console.kt` carries the console API,
// `File.kt` carries the file-query API, and `Sysinfo.kt` carries the
// system-information API. Each is visible on every KMP target; the
// FFI calls inside them are `expect`-fun declarations that only have
// real `actual` implementations on `mingwX64`.

public const val FILE_ATTRIBUTE_HIDDEN: ULong = 2uL
