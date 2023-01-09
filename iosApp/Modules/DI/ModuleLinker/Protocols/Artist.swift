import Foundation
import SwiftUI

public protocol ArtistViewProviding {
	func artistView(id: String) -> AnyView
}
