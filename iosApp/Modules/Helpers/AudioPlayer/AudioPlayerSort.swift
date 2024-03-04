import Foundation
import shared

public enum AudioPlayerSort: Codable, Equatable {
	case title(ascending: Bool)
	case artist(ascending: Bool)
	case duration(ascending: Bool)
	case noSort
	
	public var comparator: (NFTTrack, NFTTrack) -> (Bool) {
		return switch self {
		case .artist(let ascending): { track1, track2 in
			guard let first = track1.artists.first, let second = track2.artists.first else { return false }
			if ascending {
				return first < second
			} else {
				return second < first
			}
		}
		case .duration(let ascending): { track1, track2 in
			if ascending {
				return track1.duration < track2.duration
			} else {
				return track2.duration < track1.duration
			}
		}
		case .title(let ascending): { track1, track2 in
			if ascending {
				return track1.title < track2.title
			} else {
				return track2.title < track1.title
			}
		}
		case .noSort: { track1, track2 in
			true
		}
		}
	}
}
