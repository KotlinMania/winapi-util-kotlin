// port-lint: source src/console.rs
package io.github.kotlinmania.winapiutil

// Foreground color bit flags matching the Windows console API.
internal const val FG_BLUE: UShort = 1u
internal const val FG_GREEN: UShort = 2u
internal const val FG_RED: UShort = 4u
internal const val FG_INTENSITY: UShort = 8u

internal const val FG_CYAN: UShort = 3u
internal const val FG_MAGENTA: UShort = 5u
internal const val FG_YELLOW: UShort = 6u
internal const val FG_WHITE: UShort = 7u

// The set of available colors for use with a Windows console.
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

    internal fun toBg(): UShort = (toFg().toInt() shl 4).toUShort()

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

// Whether to use intense colors or not.
public enum class Intense {
    Yes,
    No,
    ;

    internal fun toFg(): UShort =
        when (this) {
            No -> 0u
            Yes -> FG_INTENSITY
        }

    internal fun toBg(): UShort = (toFg().toInt() shl 4).toUShort()

    internal companion object {
        internal fun fromFg(word: UShort): Intense =
            if ((word.toInt() and FG_INTENSITY.toInt()) > 0) Yes else No

        internal fun fromBg(word: UShort): Intense =
            fromFg((word.toInt() shr 4).toUShort())
    }
}

// A representation of text attributes for the Windows console.
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

// Defines the coordinates of the upper left and lower right corners of
// a rectangle.
//
// This corresponds to SMALL_RECT.
public data class SmallRect(
    public val left: Short,
    public val top: Short,
    public val right: Short,
    public val bottom: Short,
)

// Represents console screen buffer information such as size, cursor
// position and styling attributes.
//
// This wraps a CONSOLE_SCREEN_BUFFER_INFO.
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
    // Returns the size of the console screen buffer, in character
    // columns and rows.
    public fun size(): Pair<Short, Short> = Pair(sizeX, sizeY)

    // Returns the position of the cursor in terms of column and row
    // coordinates of the console screen buffer.
    public fun cursorPosition(): Pair<Short, Short> = Pair(cursorX, cursorY)

    // Returns the character attributes associated with this console.
    public fun attributes(): UShort = attributesValue

    // Returns the maximum size of the console window, in character
    // columns and rows, given the current screen buffer size and font
    // and the screen size.
    public fun maxWindowSize(): Pair<Short, Short> = Pair(maxWindowX, maxWindowY)

    // Returns the console screen buffer coordinates of the upper-left
    // and lower-right corners of the display window.
    public fun windowRect(): SmallRect =
        SmallRect(
            left = srWindowLeft,
            top = srWindowTop,
            right = srWindowRight,
            bottom = srWindowBottom,
        )
}
