// port-lint: source src/console.rs
package io.github.kotlinmania.winapiutil

// Query the given handle for information about the console's screen
// buffer.
//
// The given handle should represent a console. Otherwise, an error is
// returned.
public expect fun screenBufferInfo(h: AsHandleRef): ScreenBufferInfo

// Set the text attributes of the console represented by the given
// handle.
public expect fun setTextAttributes(h: AsHandleRef, attributes: UShort)

// Query the mode of the console represented by the given handle.
public expect fun consoleMode(h: AsHandleRef): UInt

// Set the mode of the console represented by the given handle.
public expect fun setConsoleMode(h: AsHandleRef, mode: UInt)

// A Windows console.
//
// This represents a very limited set of functionality available to a
// Windows console. In particular, it can only change text attributes
// such as color and intensity.
//
// There is no way to "write" to this console. Simply write to stdout
// or stderr instead, while interleaving instructions to the console
// to change text attributes.
//
// A common pitfall when using a console is to forget to flush writes
// to stdout before setting new text attributes.
public class Console internal constructor(
    private val kind: HandleKind,
    private val startAttr: TextAttributes,
    private var curAttr: TextAttributes,
) {
    // Apply the current text attributes.
    private fun set() {
        setTextAttributes(kind.handle(), curAttr.toWord())
    }

    // Apply the given intensity and color attributes to the console
    // foreground.
    public fun fg(intense: Intense, color: Color) {
        curAttr.fgColor = color
        curAttr.fgIntense = intense
        set()
    }

    // Apply the given intensity and color attributes to the console
    // background.
    public fun bg(intense: Intense, color: Color) {
        curAttr.bgColor = color
        curAttr.bgIntense = intense
        set()
    }

    // Reset the console text attributes to their original settings.
    //
    // The original settings correspond to the text attributes on the
    // console when this [Console] value was created.
    public fun reset() {
        curAttr = startAttr
        set()
    }

    // Toggle virtual terminal processing.
    //
    // When virtual terminal processing is enabled, characters emitted
    // to the console are parsed for VT100 and similar control
    // character sequences that control color and other similar
    // operations.
    public fun setVirtualTerminalProcessing(yes: Boolean) {
        val vt = ENABLE_VIRTUAL_TERMINAL_PROCESSING

        val handle = kind.handle()
        val oldMode = consoleMode(handle)
        val newMode = if (yes) oldMode or vt else oldMode and vt.inv()
        if (oldMode == newMode) {
            return
        }
        setConsoleMode(handle, newMode)
    }

    public companion object {
        // Create a new Console to stdout.
        public fun stdout(): Console = createForStream(HandleKind.Stdout)

        // Create a new Console to stderr.
        public fun stderr(): Console = createForStream(HandleKind.Stderr)

        private fun createForStream(kind: HandleKind): Console {
            val h = kind.handle()
            val info = screenBufferInfo(h)
            val attr = TextAttributes.fromWord(info.attributes())
            return Console(kind, attr, attr)
        }
    }
}

internal enum class HandleKind {
    Stdout,
    Stderr,
    ;

    internal fun handle(): HandleRef =
        when (this) {
            Stdout -> HandleRef.stdout()
            Stderr -> HandleRef.stderr()
        }
}

// ENABLE_VIRTUAL_TERMINAL_PROCESSING = 0x0004
internal const val ENABLE_VIRTUAL_TERMINAL_PROCESSING: UInt = 4u
