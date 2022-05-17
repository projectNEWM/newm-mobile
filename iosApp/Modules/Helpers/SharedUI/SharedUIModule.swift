import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public struct SharedUIModule: ModuleProtocol {
	public static let shared = SharedUIModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as IDLinking
		}
		
		Resolver.register {
			self as GradientTagProviding
		}
		
		Resolver.register {
			self as CircularProviding
		}
	}
}

extension SharedUIModule: IDLinking {
	public func idLink<LinkedView: DataView>(selectedID: String?, linkedView: LinkedView.Type) -> AnyView {
		IDLink<LinkedView>(selectedID: selectedID).erased
	}
}
	
extension SharedUIModule: GradientTagProviding {
	public func gradientTag(title: String) -> AnyView {
		GradientTag(title: title).erased
	}
}

extension SharedUIModule: CircularProviding {
	public func circular<D : RandomAccessCollection, I : Hashable, C: View>(@ViewBuilder content: () -> ForEach<D, I, C>) -> AnyView {
		Circular(content: content).erased
	}
}

#if DEBUG
extension SharedUIModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			self as TestImageProvider
		}
	}
}

extension SharedUIModule: TestImageProvider {
	public func url(for testImage: TestImage) -> String {
		guard let imageURL = NSURL(fileURLWithPath: NSTemporaryDirectory()).appendingPathComponent("TempImage.png") else {
			fatalError()
		}

		let pngData = image(for: testImage).pngData()
		do { try pngData?.write(to: imageURL) } catch { }
		return imageURL.absoluteString
	}
	
	public func image(for testImage: TestImage) -> UIImage {
		switch testImage {
		case .bowie:
			return .Bowie
		}
	}
}

#endif
