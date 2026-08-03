// port-lint: source src/win.rs
package io.github.kotlinmania.winapiutil

import io.github.kotlinmania.windowssys.windows.win32.foundation.HANDLE

// A handle represents an owned and valid Windows handle to a file-like
// object.
//
// When an owned handle is dropped, then the underlying raw handle is
// closed. To get a borrowed handle, use [HandleRef].
public class Handle private constructor(
    public val rawHandle: HANDLE,
) : AsHandleRef {
    override fun asHandleRef(): HandleRef = HandleRef(rawHandle)

    public companion object {
        // Create an owned handle from a raw Windows file handle.
        //
        // When the returned handle is dropped, the file is closed.
        //
        // Note that if the given file represents a handle to a
        // directory, then it is generally required that it have been
        // opened with the FILE_FLAG_BACKUP_SEMANTICS flag in order to
        // use it in various calls such as [information] or [typ]. To
        // have this done automatically for you, use the [fromPathAny]
        // constructor.
        public fun fromRawHandle(handle: HANDLE): Handle = Handle(handle)

        // Open a file to the given file path, and return an owned
        // handle to that file.
        //
        // When the returned handle is dropped, the file is closed.
        //
        // If there was a problem opening the file, then the
        // corresponding error is returned.
        public fun fromPath(path: String): Handle =
            Handle(openRawHandleForRead(path))

        // Like [fromPath], but supports opening directory handles as
        // well. If you use [fromPath] on a directory, then subsequent
        // queries using that handle will fail.
        public fun fromPathAny(path: String): Handle =
            Handle(openRawHandleForReadAny(path))
    }
}

// Represents a borrowed and valid Windows handle to a file-like
// object, such as stdin/stdout/stderr or an actual file.
//
// When a borrowed handle is dropped, then the underlying raw handle
// is **not** closed. To get an owned handle, use [Handle].
public class HandleRef public constructor(
    public val rawHandle: HANDLE,
) : AsHandleRef {
    override fun asHandleRef(): HandleRef = this

    public companion object {
        // Create a borrowed handle to stdin. When the returned handle
        // is dropped, stdin is not closed.
        public fun stdin(): HandleRef = HandleRef(stdinRawHandle())

        // Create a handle to stdout. When the returned handle is
        // dropped, stdout is not closed.
        public fun stdout(): HandleRef = HandleRef(stdoutRawHandle())

        // Create a handle to stderr. When the returned handle is
        // dropped, stderr is not closed.
        public fun stderr(): HandleRef = HandleRef(stderrRawHandle())
    }
}

// Construct borrowed and valid Windows handles from file-like objects.
public interface AsHandleRef {
    // A borrowed handle that wraps the raw handle of the [self]
    // object.
    public fun asHandleRef(): HandleRef

    // A convenience routine for extracting a [HandleRef] from [self],
    // and then extracting a raw handle from the [HandleRef].
    public fun asRaw(): HANDLE = asHandleRef().rawHandle
}
