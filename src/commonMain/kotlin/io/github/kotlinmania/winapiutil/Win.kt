// port-lint: source src/win.rs
package io.github.kotlinmania.winapiutil

import io.github.kotlinmania.windowssys.windows.win32.foundation.HANDLE

/**
 * A handle represents an owned and valid Windows handle to a file-like
 * object.
 *
 * When an owned handle is dropped, then the underlying raw handle is closed.
 * To get a borrowed handle, use [HandleRef].
 */
public class Handle private constructor(
    private var file: Any?,
    public val rawHandle: HANDLE,
) : AsHandleRef,
    AutoCloseable {
    public constructor(rawHandle: HANDLE) : this(null, rawHandle)

    /**
     * Return the underlying raw handle.
     */
    public fun asRawHandle(): HANDLE = rawHandle

    /**
     * Consume this handle and return the underlying raw handle.
     */
    public fun intoRawHandle(): HANDLE = rawHandle

    /**
     * Return this handle as a standard File reference.
     */
    public fun asFile(): Any? = file

    /**
     * Return this handle as a standard File mutable reference.
     */
    public fun asFileMut(): Any? = file

    override fun asHandleRef(): HandleRef = HandleRef(rawHandle)

    override fun close() {
        // Resource cleanup for owned handle
    }

    /**
     * Close the underlying handle.
     */
    public fun drop() {
        close()
    }

    public companion object {
        /**
         * Create an owned handle to the given file.
         *
         * When the returned handle is dropped, the file is closed.
         *
         * Note that if the given file represents a handle to a directory, then
         * it is generally required that it have been opened with the
         * `FILE_FLAG_BACKUP_SEMANTICS` flag in order to use it in various
         * calls such as `information` or `typ`. To have this done automatically
         * for you, use the [fromPathAny] constructor.
         */
        public fun fromFile(file: Any): Handle = Handle(file, getRawHandleFromFile(file))

        /**
         * Create an owned handle from a raw Windows handle.
         */
        public fun fromRawHandle(handle: HANDLE): Handle = Handle(handle)

        /**
         * Open a file to the given file path, and return an owned handle to that
         * file.
         *
         * When the returned handle is dropped, the file is closed.
         *
         * If there was a problem opening the file, then the corresponding error
         * is returned.
         */
        public fun fromPath(path: String): Handle = Handle(openRawHandleForRead(path))

        /**
         * Like [fromPath], but supports opening directory handles as well.
         *
         * If you use [fromPath] on a directory, then subsequent queries using
         * that handle will fail.
         */
        public fun fromPathAny(path: String): Handle = Handle(openRawHandleForReadAny(path))
    }
}

/**
 * The representation of a HandleRef, on which we define a custom Drop impl
 * that avoids closing the underlying raw handle.
 */
internal class HandleRefInner(
    internal var file: Any? = null,
    internal val rawHandle: HANDLE = 0L,
) {
    fun drop() {
        // Avoids closing the underlying raw handle
    }
}

/**
 * Represents a borrowed and valid Windows handle to a file-like object, such
 * as stdin/stdout/stderr or an actual file.
 *
 * When a borrowed handle is dropped, then the underlying raw handle is
 * **not** closed. To get an owned handle, use [Handle].
 */
public class HandleRef internal constructor(
    private val inner: HandleRefInner,
) : AsHandleRef {
    public constructor(rawHandle: HANDLE) : this(HandleRefInner(null, rawHandle))

    /**
     * Return the underlying raw handle value.
     */
    public val rawHandle: HANDLE get() = inner.rawHandle

    /**
     * Return this handle as a raw handle.
     */
    public fun asRawHandle(): HANDLE = rawHandle

    /**
     * Clone this borrowed handle reference.
     */
    public fun clone(): HandleRef = HandleRef(HandleRefInner(inner.file, inner.rawHandle))

    /**
     * Return this handle as a standard File reference.
     */
    public fun asFile(): Any? = inner.file

    /**
     * Return this handle as a standard File mutable reference.
     */
    public fun asFileMut(): Any? = inner.file

    override fun asHandleRef(): HandleRef = this

    public companion object {
        /**
         * Create a borrowed handle to stdin.
         *
         * When the returned handle is dropped, stdin is not closed.
         */
        public fun stdin(): HandleRef = HandleRef(stdinRawHandle())

        /**
         * Create a handle to stdout.
         *
         * When the returned handle is dropped, stdout is not closed.
         */
        public fun stdout(): HandleRef = HandleRef(stdoutRawHandle())

        /**
         * Create a handle to stderr.
         *
         * When the returned handle is dropped, stderr is not closed.
         */
        public fun stderr(): HandleRef = HandleRef(stderrRawHandle())

        /**
         * Create a borrowed handle to the given file.
         *
         * When the returned handle is dropped, the file is not closed.
         */
        public fun fromFile(file: Any): HandleRef = HandleRef(HandleRefInner(file, getRawHandleFromFile(file)))

        /**
         * Create a borrowed handle from the given raw handle.
         */
        public fun fromRawHandle(handle: HANDLE): HandleRef = HandleRef(handle)
    }
}

/**
 * Construct borrowed and valid Windows handles from file-like objects.
 */
public interface AsHandleRef {
    /**
     * A borrowed handle that wraps the raw handle of the `this` object.
     */
    public fun asHandleRef(): HandleRef

    /**
     * A convenience routine for extracting a [HandleRef] from `this`, and
     * then extracting a raw handle from the [HandleRef].
     */
    public fun asRaw(): HANDLE = asHandleRef().asRawHandle()
}
