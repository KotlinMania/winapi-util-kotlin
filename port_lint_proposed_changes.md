# port-lint Proposed Changes

**Generated:** 2026-08-24
**Source:** tmp/winapi-util
**Target:** src

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `commonMain/kotlin/io/github/kotlinmania/winapiutil/File.kt` | `// port-lint: source file.rs` | `// port-lint: source file.rs` | `file.rs` | `port-lint provenance header matched only after fallback normalization: 'file.rs' vs expected 'file.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/winapiutil/FileTest.kt` | `// port-lint: tests file.rs` | `// port-lint: tests file.rs` | `file.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:file.rs' vs expected 'file.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/winapiutil/Console.kt` | `// port-lint: source console.rs` | `// port-lint: source console.rs` | `console.rs` | `port-lint provenance header matched only after fallback normalization: 'console.rs' vs expected 'console.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/winapiutil/ConsoleTest.kt` | `// port-lint: tests console.rs` | `// port-lint: tests console.rs` | `console.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:console.rs' vs expected 'console.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/winapiutil/PlatformWin.kt` | `// port-lint: source win.rs` | `// port-lint: source win.rs` | `win.rs` | `port-lint provenance header matched only after fallback normalization: 'win.rs' vs expected 'win.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/winapiutil/Win.kt` | `// port-lint: source win.rs` | `// port-lint: source win.rs` | `win.rs` | `port-lint provenance header matched only after fallback normalization: 'win.rs' vs expected 'win.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/winapiutil/Sysinfo.kt` | `// port-lint: source sysinfo.rs` | `// port-lint: source sysinfo.rs` | `sysinfo.rs` | `port-lint provenance header matched only after fallback normalization: 'sysinfo.rs' vs expected 'sysinfo.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/winapiutil/SysinfoTest.kt` | `// port-lint: tests sysinfo.rs` | `// port-lint: tests sysinfo.rs` | `sysinfo.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:sysinfo.rs' vs expected 'sysinfo.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/winapiutil/Lib.kt` | `// port-lint: source lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'lib.rs' vs expected 'lib.rs'` |
