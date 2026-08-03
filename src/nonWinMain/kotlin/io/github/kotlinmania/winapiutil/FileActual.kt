// port-lint: source src/file.rs
// Non-Windows stub actuals for file FFI functions.
package io.github.kotlinmania.winapiutil

public actual fun information(h: AsHandleRef): Information =
    throw UnsupportedOperationException("winapi-util file is only available on Windows")

public actual fun typ(h: AsHandleRef): Type =
    throw UnsupportedOperationException("winapi-util file is only available on Windows")
