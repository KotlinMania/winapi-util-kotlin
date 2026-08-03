// port-lint: source src/console.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapiutil

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.value
import platform.windows._CONSOLE_SCREEN_BUFFER_INFO
import platform.windows.GetConsoleMode as winGetConsoleMode
import platform.windows.GetConsoleScreenBufferInfo as winGetConsoleScreenBufferInfo
import platform.windows.SetConsoleMode as winSetConsoleMode
import platform.windows.SetConsoleTextAttribute as winSetConsoleTextAttribute

public actual fun screenBufferInfo(h: AsHandleRef): ScreenBufferInfo {
    memScoped {
        val info = alloc<_CONSOLE_SCREEN_BUFFER_INFO>()
        val rc = winGetConsoleScreenBufferInfo(h.asRaw().toCPointer(), info.ptr)
        if (rc == 0) {
            throw RuntimeException("GetConsoleScreenBufferInfo failed")
        }
        return ScreenBufferInfo(
            sizeX = info.dwSize.X,
            sizeY = info.dwSize.Y,
            cursorX = info.dwCursorPosition.X,
            cursorY = info.dwCursorPosition.Y,
            attributesValue = info.wAttributes,
            maxWindowX = info.dwMaximumWindowSize.X,
            maxWindowY = info.dwMaximumWindowSize.Y,
            srWindowLeft = info.srWindow.Left,
            srWindowTop = info.srWindow.Top,
            srWindowRight = info.srWindow.Right,
            srWindowBottom = info.srWindow.Bottom,
        )
    }
}

public actual fun setTextAttributes(h: AsHandleRef, attributes: UShort) {
    val rc = winSetConsoleTextAttribute(h.asRaw().toCPointer(), attributes)
    if (rc == 0) {
        throw RuntimeException("SetConsoleTextAttribute failed")
    }
}

public actual fun consoleMode(h: AsHandleRef): UInt =
    memScoped {
        val modeVar = alloc<UIntVar>()
        val rc = winGetConsoleMode(h.asRaw().toCPointer(), modeVar.ptr)
        if (rc == 0) {
            throw RuntimeException("GetConsoleMode failed")
        }
        modeVar.value
    }

public actual fun setConsoleMode(h: AsHandleRef, mode: UInt) {
    val rc = winSetConsoleMode(h.asRaw().toCPointer(), mode)
    if (rc == 0) {
        throw RuntimeException("SetConsoleMode failed")
    }
}
