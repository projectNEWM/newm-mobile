import Foundation
import SwiftUI

public protocol GradientTagProviding {
	func gradientTag(title: String) -> AnyView
}

public protocol DataView: View {
	init(id: String)
}

public protocol CircularProviding {
	func circular<D : RandomAccessCollection, I : Hashable, C: View>(@ViewBuilder content: () -> ForEach<D, I, C>) -> AnyView
}

public struct TitleSectionModel {
	public let isGreeting: Bool
	public let title: String
	public let profilePic: URL?
	public let gradientColors: [String]
	
	public init(isGreeting: Bool = false, title: String, profilePic: URL? = nil, gradientColors: [String]) {
		self.isGreeting = isGreeting
		self.title = title
		self.profilePic = profilePic
		self.gradientColors = gradientColors
	}
}
