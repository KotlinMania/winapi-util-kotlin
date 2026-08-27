// port-lint: tests winapi-util/src/lib.rs
package io.github.kotlinmania.winapiutil

import kotlin.test.Test
import kotlin.test.assertEquals

class LibTest {
    @Test
    fun testModuleConstants() {
        assertEquals("console", WinapiUtil.MODULE_CONSOLE)
        assertEquals("file", WinapiUtil.MODULE_FILE)
        assertEquals("sysinfo", WinapiUtil.MODULE_SYSINFO)
    }
}
