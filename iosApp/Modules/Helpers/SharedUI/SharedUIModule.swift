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
			self as CircleImageProviding
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
	
extension SharedUIModule: CircleImageProviding {
	public func circleImage(_ image: UIImage, size: CGFloat) -> AnyView {
		CircleImage(image, size: size).erased
	}
	public func circleImage(_ image: Image, size: CGFloat) -> AnyView {
		CircleImage(image, size: size).erased
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
	}
}
#endif
