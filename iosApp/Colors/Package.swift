// swift-tools-version:5.5
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "Colors",
	platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "Colors",
            targets: ["Colors"]),
    ],
    dependencies: [
    ],
    targets: [
        .target(
            name: "Colors",
            dependencies: []),
        .testTarget(
            name: "ColorsTests",
            dependencies: ["Colors"]),
    ]
)
