/*
 * Copyright 2010-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

package runtime.basic.cleaner0

import kotlin.test.*

import kotlin.native.internal.createCleaner
import kotlin.native.internal.GC
import kotlin.native.internal.NativePtr

class Reporter(private val reporter: () -> Unit) {
    fun report() {
        reporter()
    }
}

class ReporterWithCleaner(reporter: () -> Unit) {
    var impl: Reporter? = Reporter(reporter)
    private val cleaner = createCleaner(impl!!) {
        it.report()
    }
}

@Test
fun testReporterWithCleaner() {
    var reported = false
    {
        ReporterWithCleaner { reported = true }
        assertFalse(reported)
    }()
    GC.collect()
    assertTrue(reported)
}

@Test
fun testReporterWithCleanerExplicitlyNull() {
    var reported = false
    {
        var x: ReporterWithCleaner? = null
        {
            x = ReporterWithCleaner { reported = true }
            x!!.impl = null
        }()
        GC.collect()
        assertNull(x!!.impl)
        assertFalse(reported)
    }()
    GC.collect()
    assertTrue(reported)
}

var globalInt: Int = 0

@Test
fun testCleanerWithPrimitiveType() {
    {
        createCleaner(42) {
            globalInt = it
        }
        assertEquals(0, globalInt)
    }()
    GC.collect()
    assertEquals(42, globalInt)
}

var globalPtr: NativePtr = NativePtr.NULL

@Test
fun testCleanerWithNativePtr() {
    {
        createCleaner(NativePtr.NULL + 42L) {
            globalPtr = it
        }
        assertEquals(NativePtr.NULL, globalPtr)
    }()
    GC.collect()
    assertEquals(NativePtr.NULL + 42L, globalPtr)
}
