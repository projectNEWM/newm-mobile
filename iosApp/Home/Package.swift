// swift-tools-version:5.5
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
	name: "Home",
	platforms: [
		.iOS(.v15)
	],
	products: [
		.library(
			name: "Home",
			targets: ["Home"]),
	],
	dependencies: [
		.package(name: "Fonts", path: "../Fonts"),
		.package(name: "SharedUI", path: "../SharedUI"),
		.package(name: "Colors", path: "../Colors"),
		.package(name: "Utilities", path: "../Utilities"),
		.package(name: "Strings", path: "../Strings")
	],
	targets: [
		.target(
			name: "Home",
			dependencies: [
				"Fonts",
				"SharedUI",
				"Colors",
				"Utilities",
				"Strings"
			])
	]
)
