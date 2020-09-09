/*
 * Copyright 2010-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

#include "Cleaner.h"

// Defined in Cleaner.kt
extern "C" void Kotlin_CleanerImpl_clean(KRef thiz);

RUNTIME_NOTHROW void DisposeCleaner(KRef thiz) {
#if KONAN_NO_EXCEPTIONS
    Kotlin_CleanerImpl_clean(thiz);
#else
    try {
        Kotlin_CleanerImpl_clean(thiz);
    } catch (...) {
        // A trick to terminate with unhandled exception. This will print a stack trace
        // and write to iOS crash log.
        std::terminate();
    }
#endif
}
