// port-lint: source src/console.rs
package io.github.kotlinmania.winapiutil

// Foreground color bit flags matching the Windows console API.
internal const val FG_BLUE: UShort = 1u
internal const val FG_GREEN: UShort = 2u
internal const val FG_INTENSITY: UShort = 8u
internal const val FG_RED: UShort = 4u

internal const val FG_CYAN: UShort = 3u
internal const val FG_MAGENTA: UShort = 5u
internal const val FG_YELLOW: UShort = 6u
internal const val FG_WHITE: UShort = 7u

/**
 * Query the given handle for information about the console's screen
 * buffer.
 *
 * The given handle should represent a console. Otherwise, an error is
 * returned.
 *
 * This corresponds to calling `GetConsoleScreenBufferInfo`.
 */
public fun screenBufferInfo(h: AsHandleRef): ScreenBufferInfo = getScreenBufferInfo(h)

internal expect fun getScreenBufferInfo(h: AsHandleRef): ScreenBufferInfo

/**
 * Set the text attributes of the console represented by the given
 * handle.
 *
 * This corresponds to calling `SetConsoleTextAttribute`.
 */
public fun setTextAttributes(h: AsHandleRef, attributes: UShort) {
    setConsoleTextAttributes(h, attributes)
}

internal expect fun setConsoleTextAttributes(h: AsHandleRef, attributes: UShort)

/**
 * Query the mode of the console represented by the given handle.
 *
 * This corresponds to calling `GetConsoleMode`, which describes the return
 * value.
 */
public fun consoleMode(h: AsHandleRef): UInt = getConsoleMode(h)

public fun mode(h: AsHandleRef): UInt = consoleMode(h)

internal expect fun getConsoleMode(h: AsHandleRef): UInt

/**
 * Set the mode of the console represented by the given handle.
 *
 * This corresponds to calling `SetConsoleMode`, which describes the format
 * of the mode parameter.
 */
public fun setConsoleMode(h: AsHandleRef, mode: UInt) {
    setConsoleModePlatform(h, mode)
}

public fun setMode(h: AsHandleRef, mode: UInt) {
    setConsoleMode(h, mode)
}

internal expect fun setConsoleModePlatform(h: AsHandleRef, mode: UInt)

/**
 * Represents console screen buffer information such as size, cursor
 * position and styling attributes.
 *
 * This wraps a `CONSOLE_SCREEN_BUFFER_INFO`.
 */
public class ScreenBufferInfo internal constructor(
    internal val sizeX: Short,
    internal val sizeY: Short,
    internal val cursorX: Short,
    internal val cursorY: Short,
    internal val attributesValue: UShort,
    internal val maxWindowX: Short,
    internal val maxWindowY: Short,
    internal val srWindowLeft: Short,
    internal val srWindowTop: Short,
    internal val srWindowRight: Short,
    internal val srWindowBottom: Short,
) {
    /**
     * Returns the size of the console screen buffer, in character
     * columns and rows.
     *
     * This corresponds to `dwSize`.
     */
    public fun size(): Pair<Short, Short> = Pair(sizeX, sizeY)

    /**
     * Returns the position of the cursor in terms of column and row
     * coordinates of the console screen buffer.
     *
     * This corresponds to `dwCursorPosition`.
     */
    public fun cursorPosition(): Pair<Short, Short> = Pair(cursorX, cursorY)

    /**
     * Returns the character attributes associated with this console.
     *
     * This corresponds to `wAttributes`.
     */
    public fun attributes(): UShort = attributesValue

    /**
     * Returns the maximum size of the console window, in character
     * columns and rows, given the current screen buffer size and font
     * and the screen size.
     *
     * This corresponds to `dwMaximumWindowSize`.
     */
    public fun maxWindowSize(): Pair<Short, Short> = Pair(maxWindowX, maxWindowY)

    /**
     * Returns the console screen buffer coordinates of the upper-left
     * and lower-right corners of the display window.
     *
     * This corresponds to `srWindow`.
     */
    public fun windowRect(): SmallRect =
        SmallRect(
            left = srWindowLeft,
            top = srWindowTop,
            right = srWindowRight,
            bottom = srWindowBottom,
        )
}

/**
 * Defines the coordinates of the upper left and lower right corners of
 * a rectangle.
 *
 * This corresponds to `SMALL_RECT`.
 */
public data class SmallRect(
    public val left: Short,
    public val top: Short,
    public val right: Short,
    public val bottom: Short,
)

/**
 * A Windows console.
 *
 * This represents a very limited set of functionality available to a
 * Windows console. In particular, it can only change text attributes
 * such as color and intensity.
 *
 * There is no way to "write" to this console. Simply write to stdout
 * or stderr instead, while interleaving instructions to the console
 * to change text attributes.
 *
 * A common pitfall when using a console is to forget to flush writes
 * to stdout before setting new text attributes.
 */
public class Console internal constructor(
    private val kind: HandleKind,
    private val startAttr: TextAttributes,
    private var curAttr: TextAttributes,
) {
    /**
     * Apply the current text attributes.
     */
    private fun set() {
        setTextAttributes(kind.handle(), curAttr.toWord())
    }

    /**
     * Apply the given intensity and color attributes to the console
     * foreground.
     */
    public fun fg(intense: Intense, color: Color) {
        curAttr.fgColor = color
        curAttr.fgIntense = intense
        set()
    }

    /**
     * Apply the given intensity and color attributes to the console
     * background.
     */
    public fun bg(intense: Intense, color: Color) {
        curAttr.bgColor = color
        curAttr.bgIntense = intense
        set()
    }

    /**
     * Reset the console text attributes to their original settings.
     *
     * The original settings correspond to the text attributes on the
     * console when this [Console] value was created.
     */
    public fun reset() {
        curAttr = startAttr
        set()
    }

    /**
     * Toggle virtual terminal processing.
     *
     * When virtual terminal processing is enabled, characters emitted
     * to the console are parsed for VT100 and similar control
     * character sequences that control color and other similar
     * operations.
     */
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
        /**
         * Create a new Console to stdout.
         */
        public fun stdout(): Console = createForStream(HandleKind.Stdout)

        /**
         * Create a new Console to stderr.
         */
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

/**
 * A representation of text attributes for the Windows console.
 */
internal data class TextAttributes(
    var fgColor: Color = Color.Black,
    var fgIntense: Intense = Intense.No,
    var bgColor: Color = Color.Black,
    var bgIntense: Intense = Intense.No,
) {
    internal fun toWord(): UShort {
        var w: UShort = 0u
        w = (w.toInt() or fgColor.toFg().toInt()).toUShort()
        w = (w.toInt() or fgIntense.toFg().toInt()).toUShort()
        w = (w.toInt() or bgColor.toBg().toInt()).toUShort()
        w = (w.toInt() or bgIntense.toBg().toInt()).toUShort()
        return w
    }

    internal companion object {
        internal fun fromWord(word: UShort): TextAttributes =
            TextAttributes(
                fgColor = Color.fromFg(word),
                fgIntense = Intense.fromFg(word),
                bgColor = Color.fromBg(word),
                bgIntense = Intense.fromBg(word),
            )
    }
}

/**
 * Whether to use intense colors or not.
 */
public enum class Intense {
    Yes,
    No,
    ;

    internal fun toBg(): UShort = (toFg().toInt() shl 4).toUShort()

    internal fun toFg(): UShort =
        when (this) {
            No -> 0u
            Yes -> FG_INTENSITY
        }

    internal companion object {
        internal fun fromFg(word: UShort): Intense =
            if ((word.toInt() and FG_INTENSITY.toInt()) > 0) Yes else No

        internal fun fromBg(word: UShort): Intense =
            fromFg((word.toInt() shr 4).toUShort())
    }
}

/**
 * The set of available colors for use with a Windows console.
 */
public enum class Color {
    Black,
    Blue,
    Green,
    Red,
    Cyan,
    Magenta,
    Yellow,
    White,
    ;

    internal fun toBg(): UShort = (toFg().toInt() shl 4).toUShort()

    internal fun toFg(): UShort =
        when (this) {
            Black -> 0u
            Blue -> FG_BLUE
            Green -> FG_GREEN
            Red -> FG_RED
            Cyan -> FG_CYAN
            Magenta -> FG_MAGENTA
            Yellow -> FG_YELLOW
            White -> FG_WHITE
        }

    internal companion object {
        internal fun fromFg(word: UShort): Color {
            val bits = (word.toInt() and 0b111).toUShort()
            return when (bits) {
                FG_BLUE -> Blue
                FG_GREEN -> Green
                FG_RED -> Red
                FG_CYAN -> Cyan
                FG_MAGENTA -> Magenta
                FG_YELLOW -> Yellow
                FG_WHITE -> White
                else -> Black
            }
        }

        internal fun fromBg(word: UShort): Color =
            fromFg((word.toInt() shr 4).toUShort())
    }
}

internal const val ENABLE_VIRTUAL_TERMINAL_PROCESSING: UInt = 4u
