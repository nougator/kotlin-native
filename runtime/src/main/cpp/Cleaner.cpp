/*
 * Copyright 2010-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

#include "Cleaner.h"

// Defined in Cleaner.kt
extern "C" void Kotlin_CleanerImpl_clean(KRef thiz);

RUNTIME_NOTHROW void DisposeCleaner(KRef thiz) {
    // TODO: Make sure this does not throw.
    Kotlin_CleanerImpl_clean(thiz);
}
