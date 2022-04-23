import Foundation
import SwiftUI

public protocol ModuleProtocol {
	func registerAllServices()
	static var shared: Self { get }
}
