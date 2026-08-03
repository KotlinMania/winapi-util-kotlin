// port-lint: tests src/console.rs
package io.github.kotlinmania.winapiutil

import kotlin.test.Test
import kotlin.test.assertEquals

class ColorTest {
    @Test
    fun toFgReturnsCorrectBits() {
        assertEquals(0u, Color.Black.toFg())
        assertEquals(FG_BLUE, Color.Blue.toFg())
        assertEquals(FG_GREEN, Color.Green.toFg())
        assertEquals(FG_RED, Color.Red.toFg())
        assertEquals(FG_CYAN, Color.Cyan.toFg())
        assertEquals(FG_MAGENTA, Color.Magenta.toFg())
        assertEquals(FG_YELLOW, Color.Yellow.toFg())
        assertEquals(FG_WHITE, Color.White.toFg())
    }

    @Test
    fun toBgShiftsFgByFourBits() {
        for (color in Color.entries) {
            val expected = (color.toFg().toInt() shl 4).toUShort()
            assertEquals(expected, color.toBg())
        }
    }

    @Test
    fun fromFgRoundTrips() {
        for (color in Color.entries) {
            val word = color.toFg()
            assertEquals(color, Color.fromFg(word))
        }
    }

    @Test
    fun fromBgRoundTrips() {
        for (color in Color.entries) {
            val word = color.toBg()
            assertEquals(color, Color.fromBg(word))
        }
    }

    @Test
    fun fromFgUnknownBitsResolveToBlack() {
        assertEquals(Color.Black, Color.fromFg(0u))
    }
}

class IntenseTest {
    @Test
    fun toFgReturnsCorrectBits() {
        assertEquals(0u, Intense.No.toFg())
        assertEquals(FG_INTENSITY, Intense.Yes.toFg())
    }

    @Test
    fun toBgShiftsFgByFourBits() {
        for (intense in Intense.entries) {
            val expected = (intense.toFg().toInt() shl 4).toUShort()
            assertEquals(expected, intense.toBg())
        }
    }

    @Test
    fun fromFgRoundTrips() {
        for (intense in Intense.entries) {
            val word = intense.toFg()
            assertEquals(intense, Intense.fromFg(word))
        }
    }

    @Test
    fun fromBgRoundTrips() {
        for (intense in Intense.entries) {
            val word = intense.toBg()
            assertEquals(intense, Intense.fromBg(word))
        }
    }
}

class TextAttributesTest {
    @Test
    fun toWordCombinesAllComponents() {
        val attr =
            TextAttributes(
                fgColor = Color.Cyan,
                fgIntense = Intense.Yes,
                bgColor = Color.Red,
                bgIntense = Intense.No,
            )
        val expected =
            (
                FG_CYAN.toInt() or FG_INTENSITY.toInt() or
                    (FG_RED.toInt() shl 4)
            ).toUShort()
        assertEquals(expected, attr.toWord())
    }

    @Test
    fun fromWordRoundTrips() {
        val original =
            TextAttributes(
                fgColor = Color.Magenta,
                fgIntense = Intense.Yes,
                bgColor = Color.Yellow,
                bgIntense = Intense.Yes,
            )
        val word = original.toWord()
        val restored = TextAttributes.fromWord(word)
        assertEquals(original, restored)
    }

    @Test
    fun defaultAttributesAreAllBlackNo() {
        val attr = TextAttributes()
        assertEquals(Color.Black, attr.fgColor)
        assertEquals(Intense.No, attr.fgIntense)
        assertEquals(Color.Black, attr.bgColor)
        assertEquals(Intense.No, attr.bgIntense)
        assertEquals(0u, attr.toWord())
    }
}
