# port-lint Proposed Changes

**Generated:** 2026-08-31
**Source:** tmp/winapi-util/src
**Target:** src/commonMain/kotlin/io/github/kotlinmania/winapiutil

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/winapiutil/File.kt` | `// port-lint: source winapi-util/src/file.rs` | `// port-lint: source file.rs` | `file.rs` | `port-lint provenance header matched only after fallback normalization: 'winapi-util/src/file.rs' vs expected 'file.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/winapiutil/FileTest.kt` | `// port-lint: tests winapi-util/src/file.rs` | `// port-lint: tests file.rs` | `file.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:winapi-util/src/file.rs' vs expected 'file.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/winapiutil/Console.kt` | `// port-lint: source winapi-util/src/console.rs` | `// port-lint: source console.rs` | `console.rs` | `port-lint provenance header matched only after fallback normalization: 'winapi-util/src/console.rs' vs expected 'console.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/winapiutil/ConsoleTest.kt` | `// port-lint: tests winapi-util/src/console.rs` | `// port-lint: tests console.rs` | `console.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:winapi-util/src/console.rs' vs expected 'console.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/winapiutil/Win.kt` | `// port-lint: source winapi-util/src/win.rs` | `// port-lint: source win.rs` | `win.rs` | `port-lint provenance header matched only after fallback normalization: 'winapi-util/src/win.rs' vs expected 'win.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/winapiutil/PlatformWin.kt` | `// port-lint: source winapi-util/src/win.rs` | `// port-lint: source win.rs` | `win.rs` | `port-lint provenance header matched only after fallback normalization: 'winapi-util/src/win.rs' vs expected 'win.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/winapiutil/Sysinfo.kt` | `// port-lint: source winapi-util/src/sysinfo.rs` | `// port-lint: source sysinfo.rs` | `sysinfo.rs` | `port-lint provenance header matched only after fallback normalization: 'winapi-util/src/sysinfo.rs' vs expected 'sysinfo.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/winapiutil/SysinfoTest.kt` | `// port-lint: tests winapi-util/src/sysinfo.rs` | `// port-lint: tests sysinfo.rs` | `sysinfo.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:winapi-util/src/sysinfo.rs' vs expected 'sysinfo.rs'` |
