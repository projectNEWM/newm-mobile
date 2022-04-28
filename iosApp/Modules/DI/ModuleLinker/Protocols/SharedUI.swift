import Foundation
import SwiftUI

public protocol GradientTagProviding {
	func gradientTag(title: String) -> AnyView
}

public protocol IDLinking {
	func idLink<LinkedView: DataView>(selectedID: String?, linkedView: LinkedView.Type) -> AnyView
}

public protocol CircleImageProviding {
	func circleImage(_ image: UIImage, size: CGFloat) -> AnyView
	func circleImage(_ image: Image, size: CGFloat) -> AnyView
}

public protocol DataView: View {
	init(id: String)
}

public protocol CircularProviding {
	func circular<D : RandomAccessCollection, I : Hashable, C: View>(@ViewBuilder content: () -> ForEach<D, I, C>) -> AnyView
}
