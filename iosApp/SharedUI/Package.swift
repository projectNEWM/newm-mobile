// swift-tools-version:5.5
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "SharedUI",
	platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "SharedUI",
            targets: ["SharedUI"]),
    ],
    dependencies: [
		.package(name: "Colors", path: "../Colors"),
		.package(name: "Fonts", path: "../Fonts")
    ],
    targets: [
        .target(
            name: "SharedUI",
            dependencies: [
				"Colors",
				"Fonts"
			]),
        .testTarget(
            name: "SharedUITests",
            dependencies: ["SharedUI"]),
    ]
)
