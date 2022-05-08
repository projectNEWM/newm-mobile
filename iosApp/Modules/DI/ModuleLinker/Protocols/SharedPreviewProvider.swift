import Foundation
import SwiftUI
import Resolver

public protocol SharedPreviewProvider: PreviewProvider {
	static var sharedPreviews: Previews { get }
}

public extension SharedPreviewProvider {
	static var sharedPreviews: Previews {
		Resolver.root = Resolver.mock
		return previews
	}
}
