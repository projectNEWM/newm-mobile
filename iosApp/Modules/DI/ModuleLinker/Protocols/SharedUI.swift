import Foundation
import SwiftUI

public protocol GradientTagProviding {
	func gradientTag(title: String) -> AnyView
}

public protocol IDLinking {
	func idLink<LinkedView: DataView>(selectedID: String?, linkedView: LinkedView.Type) -> AnyView
}

public protocol DataView: View {
	init(id: String)
}

public protocol CircularProviding {
	func circular<D : RandomAccessCollection, I : Hashable, C: View>(@ViewBuilder content: () -> ForEach<D, I, C>) -> AnyView
}


#if DEBUG

public enum TestImage {
	case bowie
	case rick
}

public protocol TestImageProvider {
	func image(for testImage: TestImage) -> UIImage
	func url(for testImage: TestImage) -> String
}

#endif
