import Foundation
import SwiftUI

public protocol HomeViewProviding {
	func homeView() -> AnyView
}

public protocol HomeViewGradientProviding {
	var gradientColors: [Color] { get }
}
