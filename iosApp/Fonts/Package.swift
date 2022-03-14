// swift-tools-version:5.5
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
	name: "Fonts",
	platforms: [
		.iOS(.v15)
	],
	products: [
		.library(
			name: "Fonts",
			targets: ["Fonts"]),
	],
	dependencies: [
	],
	targets: [
		.target(
			name: "Fonts",
			dependencies: [],
			exclude: ["LICENSE.txt"],
			resources: [
				.copy("Raleway-VariableFont_wght.ttf"),
				.copy("Roboto-Regular.ttf")
			])
	]
)
