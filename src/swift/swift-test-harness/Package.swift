// swift-tools-version: 6.0
import Foundation
import PackageDescription

let swiftExportConfiguration: String = {
    let raw =
        ProcessInfo.processInfo.environment["SWIFT_EXPORT_CONFIGURATION"]
        ?? ProcessInfo.processInfo.environment["CONFIGURATION"]
        ?? "Debug"
    return raw.lowercased() == "release" ? "Release" : "Debug"
}()

let package = Package(
    name: "SwiftTestHarness",
    platforms: [
        .macOS(.v15),
    ],
    dependencies: [
        .package(name: "WinapiUtil", path: "../../../build/SPMPackage/macosArm64/\(swiftExportConfiguration)"),
    ],
    targets: [
        .testTarget(
            name: "SwiftTestHarnessTests",
            dependencies: [
                .product(name: "WinapiUtilLibrary", package: "WinapiUtil"),
            ],
            linkerSettings: [
                .unsafeFlags(
                    [
                        "-L", "../../../build/swift-test/Debug",
                        "-lWinapiUtil",
                    ],
                    .when(configuration: .debug)
                ),
                .unsafeFlags(
                    [
                        "-L", "../../../build/swift-test/Release",
                        "-lWinapiUtil",
                    ],
                    .when(configuration: .release)
                ),
            ]
        ),
    ],
    swiftLanguageModes: [.v6]
)
