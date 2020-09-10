import library.*
import kotlinx.cinterop.*
import kotlin.test.*

fun main() {
    assertNotNull(s.ptr)
    assertNotNull(u.ptr)
    assertNotNull(array)

    setContent(s.ptr, "yo")
    assertEquals("yo", getContent(s.ptr)?.toKString())

    setDouble(u.ptr, Double.MIN_VALUE)
    assertEquals(Double.MIN_VALUE, getDouble(u.ptr))

    setArrayValue(array, 0x1)
    for (i in 0 until arrayLength()) {
        assertEquals(0x1, array[i])
    }
}