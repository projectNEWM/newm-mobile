// swift-tools-version:5.5
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
	name: "NEWMApp",
	platforms: [.iOS(.v15)],
	products: [
		.library(
			name: "NEWMApp",
			targets: ["NEWMApp"]),
	],
	dependencies: [
		.package(name: "Home", path: "../Home"),
		.package(name: "TabBar", path: "../TabBar")
	],
	targets: [
		.target(
			name: "NEWMApp",
			dependencies: [
				"Home",
				"TabBar"
			]),
		.testTarget(
			name: "AppTests",
			dependencies: ["NEWMApp"]),
	]
)
