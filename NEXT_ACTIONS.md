# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 5/5 (100.0%)
- **Function parity:** 58/58 matched (target 125) — 100.0%
- **Class/type parity:** 13/14 matched (target 20) — 92.9%
- **Combined symbol parity:** 71/72 matched (target 145) — 98.6%
- **Average inline-code cosine:** 0.59 (function body across 5 matched files)
- **Average documentation cosine:** 0.07 (doc text across 5 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 3 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. file

- **Target:** `winapiutil.File [PROVENANCE-FALLBACK]`
- **Similarity:** 0.45
- **Dependents:** 0
- **Priority Score:** 11805.5
- **Functions:** 16/16 matched (target 32)
- **Missing functions:** _none_
- **Types:** 1/2 matched (target 3)
- **Missing types:** `Type`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `file.rs` vs expected `file.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:file.rs` vs expected `file.rs`
- **Proposed provenance header:** `// port-lint: source file.rs` (current: `// port-lint: source file.rs`)
- **Proposed provenance header:** `// port-lint: tests file.rs` (current: `// port-lint: tests file.rs`)
- **Lint issues:** 2

### 2. console

- **Target:** `winapiutil.ConsoleApi [PROVENANCE-FALLBACK]`
- **Similarity:** 0.64
- **Dependents:** 0
- **Priority Score:** 3103.6
- **Functions:** 24/24 matched (target 50)
- **Missing functions:** _none_
- **Types:** 7/7 matched (target 10)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `console.rs` vs expected `console.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:console.rs` vs expected `console.rs`
- **Proposed provenance header:** `// port-lint: source console.rs` (current: `// port-lint: source console.rs`)
- **Proposed provenance header:** `// port-lint: tests console.rs` (current: `// port-lint: tests console.rs`)
- **Lint issues:** 2

### 3. win

- **Target:** `winapiutil.PlatformWin [PROVENANCE-FALLBACK]`
- **Similarity:** 0.35
- **Dependents:** 0
- **Priority Score:** 1906.5
- **Functions:** 15/15 matched (target 35)
- **Missing functions:** _none_
- **Types:** 4/4 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `win.rs` vs expected `win.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `win.rs` vs expected `win.rs`
- **Proposed provenance header:** `// port-lint: source win.rs` (current: `// port-lint: source win.rs`)
- **Proposed provenance header:** `// port-lint: source win.rs` (current: `// port-lint: source win.rs`)
- **Lint issues:** 2

### 4. sysinfo

- **Target:** `winapiutil.Sysinfo [PROVENANCE-FALLBACK]`
- **Similarity:** 0.53
- **Dependents:** 0
- **Priority Score:** 404.7
- **Functions:** 3/3 matched (target 8)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 1/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `sysinfo.rs` vs expected `sysinfo.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:sysinfo.rs` vs expected `sysinfo.rs`
- **Proposed provenance header:** `// port-lint: source sysinfo.rs` (current: `// port-lint: source sysinfo.rs`)
- **Proposed provenance header:** `// port-lint: tests sysinfo.rs` (current: `// port-lint: tests sysinfo.rs`)
- **Lint issues:** 2

### 5. lib

- **Target:** `winapiutil.Lib [PROVENANCE-FALLBACK]`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 0.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source lib.rs`)
- **Lint issues:** 1

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

