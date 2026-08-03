// port-lint: source src/console.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapiutil

import kotlinx.cinterop.cValue
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.usePinned
import io.github.kotlinmania.winapiutil.cinterop.GetConsoleMode as winGetConsoleMode
import io.github.kotlinmania.winapiutil.cinterop.GetConsoleScreenBufferInfo as winGetConsoleScreenBufferInfo
import io.github.kotlinmania.winapiutil.cinterop.SetConsoleMode as winSetConsoleMode
import io.github.kotlinmania.winapiutil.cinterop.SetConsoleTextAttribute as winSetConsoleTextAttribute

public actual fun screenBufferInfo(h: AsHandleRef): ScreenBufferInfo {
    val info = cValue<CONSOLE_SCREEN_BUFFER_INFO>()
    val rc = winGetConsoleScreenBufferInfo(h.asRaw().toCPointer(), info.ptr)
    if (rc == 0) {
        throw RuntimeException("GetConsoleScreenBufferInfo failed")
    }
    info.usePinned { pinned ->
        val dwSize = pinned.dwSize
        val dwCursorPosition = pinned.dwCursorPosition
        val wAttributes = pinned.wAttributes
        val dwMaximumWindowSize = pinned.dwMaximumWindowSize
        val srWindow = pinned.srWindow
        return ScreenBufferInfo(
            sizeX = dwSize.X,
            sizeY = dwSize.Y,
            cursorX = dwCursorPosition.X,
            cursorY = dwCursorPosition.Y,
            attributesValue = wAttributes.toUShort(),
            maxWindowX = dwMaximumWindowSize.X,
            maxWindowY = dwMaximumWindowSize.Y,
            srWindowLeft = srWindow.Left,
            srWindowTop = srWindow.Top,
            srWindowRight = srWindow.Right,
            srWindowBottom = srWindow.Bottom,
        )
    }
}

public actual fun setTextAttributes(h: AsHandleRef, attributes: UShort) {
    val rc = winSetConsoleTextAttribute(h.asRaw().toCPointer(), attributes.toShort())
    if (rc == 0) {
        throw RuntimeException("SetConsoleTextAttribute failed")
    }
}

public actual fun consoleMode(h: AsHandleRef): UInt {
    val modeVar = UIntVar(0u)
    val rc = winGetConsoleMode(h.asRaw().toCPointer(), modeVar.ptr)
    if (rc == 0) {
        throw RuntimeException("GetConsoleMode failed")
    }
    return modeVar.value
}

public actual fun setConsoleMode(h: AsHandleRef, mode: UInt) {
    val rc = winSetConsoleMode(h.asRaw().toCPointer(), mode)
    if (rc == 0) {
        throw RuntimeException("SetConsoleMode failed")
    }
}
