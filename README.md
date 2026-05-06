# winapi-util-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Fwinapi--util--kotlin-blue.svg)](https://github.com/KotlinMania/winapi-util-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/winapi-util-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/winapi-util-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/winapi-util-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/winapi-util-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`BurntSushi/winapi-util`](https://github.com/BurntSushi/winapi-util).

**Original Project:** This port is based on [`BurntSushi/winapi-util`](https://github.com/BurntSushi/winapi-util). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `BurntSushi/winapi-util`

> The text below is reproduced and lightly edited from [`https://github.com/BurntSushi/winapi-util`](https://github.com/BurntSushi/winapi-util). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

## winapi-util

This crate provides a smattering of safe wrappers around various parts of the
[windows-sys](https://crates.io/crates/windows-sys) crate.

[![Build status](https://github.com/BurntSushi/winapi-util/workflows/ci/badge.svg)](https://github.com/BurntSushi/winapi-util/actions)
[![](http://meritbadge.herokuapp.com/winapi-util)](https://crates.io/crates/winapi-util)

Dual-licensed under MIT or the [UNLICENSE](http://unlicense.org).


### Documentation

https://docs.rs/winapi-util


### Usage

Run `cargo add winapi-util` to add this dependency to your `Cargo.toml` file.


### Notes

This crate was born out of frustration with having to write lots of little
ffi utility bindings in a variety of crates in order to get Windows support.
Eventually, I started needing to copy & paste a lot of those utility routines.
Since they are utility routines, they often don't make sense to expose directly
in the crate in which they are defined. Instead of continuing this process,
I decided to make a crate instead.

Normally, I'm not a huge fan of "utility" crates like this that don't have a
well defined scope, but this is primarily a practical endeavor to make it
easier to isolate Windows specific ffi code.

While I don't have a long term vision for this crate, I will welcome additional
PRs that add more high level routines/types on an as-needed basis.

**WARNING:** I am not a Windows developer, so extra review to make sure I've
got things right is most appreciated.


### Naming

This crate was originally born on top of `winapi`, and thus, it is called
`winapi-util`. As time passed, Microsoft eventually started providing their
own official `windows` and `windows-sys` crates. As a result, [`winapi` itself
got less activity](https://github.com/BurntSushi/winapi-util/pull/13#issuecomment-1991282893).
As the ecosystem moved on to `windows-sys`, it became clear that `winapi-util`
should too. Thus, its name is now officially historical and not reflective
of what it actually does.


### Minimum Rust version policy

This crate's minimum supported `rustc` version is `1.72.0`.

The current policy is that the minimum Rust version required to use this crate
can be increased in non-breaking version updates. For example, if `crate 1.0`
requires Rust 1.20.0, then `crate 1.0.z` for all values of `z` will also
require Rust 1.20.0 or newer. However, `crate 1.y` for `y > 0` may require a
newer minimum version of Rust.

In general, this crate will be conservative with respect to the minimum
supported version of Rust.

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:winapi-util-kotlin:0.1.0-SNAPSHOT")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same Unlicense license as the upstream [`BurntSushi/winapi-util`](https://github.com/BurntSushi/winapi-util). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the winapi-util authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`BurntSushi/winapi-util`](https://github.com/BurntSushi/winapi-util) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
