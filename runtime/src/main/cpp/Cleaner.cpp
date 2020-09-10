/*
 * Copyright 2010-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

#include "Cleaner.h"

namespace {

struct CleanerImpl {
    ObjHeader header;
    KRef obj;
    KNativePtr cleanObj;
};

CleanerImpl* asCleanerImpl(KRef thiz) {
    RuntimeAssert(thiz->type_info() == theCleanerImplTypeInfo, "Must be Cleaner");
    return reinterpret_cast<CleanerImpl*>(thiz);
}

} // namespace

RUNTIME_NOTHROW void DisposeCleaner(KRef thiz) {
    auto* cleaner = asCleanerImpl(thiz);
    auto* cleanObj = reinterpret_cast<void (*)(KRef)>(cleaner->cleanObj);
#if KONAN_NO_EXCEPTIONS
    cleanObj(cleaner->obj);
#else
    try {
        cleanObj(cleaner->obj);
    } catch (...) {
        // A trick to terminate with unhandled exception. This will print a stack trace
        // and write to iOS crash log.
        std::terminate();
    }
#endif
}
