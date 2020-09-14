/*
 * Copyright 2010-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

package runtime.basic.cleaner0

import kotlin.test.*

import kotlin.native.internal.createCleaner
import kotlin.native.internal.GC

class Reporter(private val reporter: () -> Unit) {
    fun report() {
        reporter()
    }
}

class ReporterWithCleaner(reporter: () -> Unit) {
    private val impl = Reporter(reporter)
    private val cleaner = createCleaner(impl) {
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
