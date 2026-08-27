// port-lint: source winapi-util/src/console.rs
package io.github.kotlinmania.winapiutil

internal actual fun getScreenBufferInfo(h: AsHandleRef): ScreenBufferInfo =
    throw UnsupportedOperationException("winapi-util console is only available on Windows")

internal actual fun setConsoleTextAttributes(h: AsHandleRef, attributes: UShort): Unit =
    throw UnsupportedOperationException("winapi-util console is only available on Windows")

internal actual fun getConsoleMode(h: AsHandleRef): UInt =
    throw UnsupportedOperationException("winapi-util console is only available on Windows")

internal actual fun setConsoleModePlatform(h: AsHandleRef, mode: UInt): Unit =
    throw UnsupportedOperationException("winapi-util console is only available on Windows")
