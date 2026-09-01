# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 5/5 (100.0%)
- **Function parity:** 58/58 matched (target 98) — 100.0%
- **Class/type parity:** 13/14 matched (target 19) — 92.9%
- **Combined symbol parity:** 71/72 matched (target 117) — 98.6%
- **Average inline-code cosine:** 0.47 (function body across 4 matched files)
- **Average documentation cosine:** 0.95 (doc text across 4 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 4 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. winapi-util.file

- **Target:** `winapiutil.File`
- **Similarity:** 0.45
- **Dependents:** 0
- **Priority Score:** 11805.5
- **Functions:** 16/16 matched (target 28)
- **Missing functions:** _none_
- **Types:** 1/2 matched (target 3)
- **Missing types:** `Type`

### 2. winapi-util.console

- **Target:** `winapiutil.Console`
- **Similarity:** 0.64
- **Dependents:** 0
- **Priority Score:** 3103.6
- **Functions:** 24/24 matched (target 42)
- **Missing functions:** _none_
- **Types:** 7/7 matched (target 10)
- **Missing types:** _none_

### 3. winapi-util.win

- **Target:** `winapiutil.Win`
- **Similarity:** 0.35
- **Dependents:** 0
- **Priority Score:** 1906.5
- **Functions:** 15/15 matched (target 23)
- **Missing functions:** _none_
- **Types:** 4/4 matched
- **Missing types:** _none_

### 4. winapi-util.sysinfo

- **Target:** `winapiutil.Sysinfo`
- **Similarity:** 0.44
- **Dependents:** 0
- **Priority Score:** 405.6
- **Functions:** 3/3 matched (target 5)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 1/1 matched

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Matched

| Source | Target | Path |
|--------|--------|------|
| `winapi-util.lib` | `winapiutil.Lib` | `winapi-util/src/lib` |

