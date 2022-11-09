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
