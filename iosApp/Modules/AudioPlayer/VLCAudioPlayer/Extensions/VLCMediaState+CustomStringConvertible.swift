import Foundation
import VLCKitSPM

extension VLCMediaState: CustomStringConvertible {
	public var description: String {
		return switch self {
		case .buffering: "buffering"
		case .nothingSpecial: "nothing special"
		case .error: "error"
		case .playing: "playing"
		default: fatalError()
		}
	}
}

