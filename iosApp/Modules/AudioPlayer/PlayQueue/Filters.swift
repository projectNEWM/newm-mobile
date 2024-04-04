import Foundation
import shared
import Utilities

public struct Filters {
	public var text: String?
	
	@UserDefault(defaultValue: 30)
	public var duration: Int?
	
	public var filter: ((NFTTrack) -> Bool)? {
		guard text != nil || duration != nil else { return nil }
		return { track in
			if let durationFilter = duration, track.duration < durationFilter {
				return false
			}
			
			if let textFilter = text, !(track.title.localizedCaseInsensitiveContains(textFilter) || track.artists.contains(where: { $0.localizedCaseInsensitiveContains(textFilter) })) {
				return false
			}
			
			return true
		}
	}
}
