// port-lint: source src/console.rs
// Non-Windows stub actuals for console FFI functions.
package io.github.kotlinmania.winapiutil

public actual fun screenBufferInfo(h: AsHandleRef): ScreenBufferInfo =
    throw UnsupportedOperationException("winapi-util console is only available on Windows")

public actual fun setTextAttributes(h: AsHandleRef, attributes: UShort): Unit = throw UnsupportedOperationException("winapi-util console is only available on Windows")

public actual fun consoleMode(h: AsHandleRef): UInt =
    throw UnsupportedOperationException("winapi-util console is only available on Windows")

public actual fun setConsoleMode(h: AsHandleRef, mode: UInt): Unit = throw UnsupportedOperationException("winapi-util console is only available on Windows")
