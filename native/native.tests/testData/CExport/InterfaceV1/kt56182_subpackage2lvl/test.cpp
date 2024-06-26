#include "kt56182_subpackage2lvl_api.h"

#include <thread>

int main() {
    auto t = std::thread([] {
        auto lib = kt56182_subpackage2lvl_symbols();

        lib->kotlin.root.knlibrary.subpackage.enableMemoryChecker();

        // Initialize A and B.Companion and get their stable pointers.
        auto a = lib->kotlin.root.knlibrary.subpackage.A._instance();
        auto bCompanion = lib->kotlin.root.knlibrary.subpackage.B.Companion._instance();

        // Now, dispose of the stable pointers.
        lib->DisposeStablePointer(bCompanion.pinned);
        lib->DisposeStablePointer(a.pinned);

        // A and B.Companion now are owned by the global references only.
    });

    // This causes Kotlin runtime full deinitialization, because `t` is the only thread
    // with the Kotlin runtime. So, all the globals will get deinitialized and memory
    // leak checker will get executed (because .kt code is compiled with -g).
    t.join();

    return 0;
}
