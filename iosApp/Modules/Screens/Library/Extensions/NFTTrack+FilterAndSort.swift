import Foundation
import shared
import AudioPlayer

extension NFTTrack {
	func isAboveDurationFilter(_ durationFilter: Int?) -> Bool {
		let trackIsAboveDurationFilter: Bool
		if let durationFilter {
			if duration > durationFilter {
				trackIsAboveDurationFilter = true
			} else {
				trackIsAboveDurationFilter = false
			}
		} else {
			trackIsAboveDurationFilter = true
		}
		return trackIsAboveDurationFilter
	}
	
	func containsSearchText(_ searchText: String?) -> Bool {
		guard let searchText, searchText.isEmpty == false else {
			return true
		}
		
		return title.localizedCaseInsensitiveContains(searchText) ||
		artists.contains { $0.localizedCaseInsensitiveContains(searchText) }
	}
}

extension [NFTTrack] {
	func filteredAndSorted(sort: Sort, searchText: String?, durationFilter: Int?) -> [NFTTrack] {
		filter { track in
			return track.isAboveDurationFilter(durationFilter) &&
			track.containsSearchText(searchText)
		}
		.sorted(by: sort.comparator)
	}
}
