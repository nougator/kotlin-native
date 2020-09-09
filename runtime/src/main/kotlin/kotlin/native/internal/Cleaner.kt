/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

package kotlin.native.internal

public interface Cleaner

@NoReorderFields
@ExportTypeInfo("theCleanerImplTypeInfo")
private class CleanerImpl<T>(
    private val obj: T,
    private val cleanObj: (T) -> Unit
): Cleaner {

    @ExportForCppRuntime("Kotlin_CleanerImpl_clean")
    private fun clean() {
        cleanObj(obj)
    }
}

/**
 * If [block] throws an exception, the entire program terminates.
 */
fun <T> createCleaner(argument: T, block: (T) -> Unit): Cleaner {
    // TODO: Make sure that block is non-capturing.
    return CleanerImpl(argument, block)
}
