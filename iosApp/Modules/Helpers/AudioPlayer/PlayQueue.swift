import Foundation
import shared
import Utilities

struct PlayQueue {
	var originalTracks: Set<NFTTrack> = [] {
		didSet {
			filterAndSortOriginalTracksIntoCurrentQueue()
		}
	}
	
	private var currentQueue: [NFTTrack] = []
	private var currentIndex: Int?
	
	var isEmpty: Bool {
		currentQueue.isEmpty
	}
	@UserDefault(defaultValue: .artist(ascending: true)) var sortCriteria: AudioPlayerSort {
		didSet {
			applySortingToCurrentQueue()
		}
	}
	var filters: Filters? {
		didSet {
			applyFiltersToCurrentQueue()
		}
	}
	@UserDefault(defaultValue: false) var shuffle: Bool {
		didSet {
			guard oldValue != shuffle else { return }
			shuffle ? shuffleCurrentQueue() : unshuffleCurrentQueue()
		}
	}
	@UserDefault(defaultValue: .none) var repeatMode: RepeatMode
	
	mutating
	private func shuffleCurrentQueue() {
		if let currentIndex = currentIndex {
			let currentTrack = currentQueue[currentIndex]
			currentQueue.shuffle()
			currentQueue.firstIndex(of: currentTrack).flatMap { currentQueue.swapAt(0, $0) }
			self.currentIndex = 0
		}
	}
	
	mutating
	private func unshuffleCurrentQueue() {
		let currentTrack = currentIndex.flatMap { currentQueue.indices.contains($0) ? currentQueue[$0] : nil }
		filterAndSortOriginalTracksIntoCurrentQueue()
		currentIndex = currentTrack.flatMap { currentQueue.firstIndex(of: $0) }
	}
	
	mutating
	private func filterAndSortOriginalTracksIntoCurrentQueue() {
		currentQueue = Array(originalTracks)
		applyFiltersToCurrentQueue()
		applySortingToCurrentQueue()
	}
	
	func currentTrack() throws -> NFTTrack? {
		guard let currentIndex else { return nil }
		guard currentQueue.indices.contains(currentIndex) else { throw PlayQueueError.invalidIndex }
		return currentQueue[currentIndex]
	}
		
	mutating
	func seekToFirst() throws {
		guard isEmpty == false else { throw PlayQueueError.queueIsEmpty }
		currentIndex = 0
	}
	
	mutating
	func nextTrack(userInitiated: Bool = false) -> NFTTrack? {
		guard !currentQueue.isEmpty, let oldIndex = currentIndex else { return nil }
		
		let newIndex: Int
		switch repeatMode {
		case .all:
			newIndex = (oldIndex + 1) % currentQueue.count
		case .one:
			if userInitiated {
				fallthrough
			} else {
				newIndex = oldIndex
			}
		case .none:
			newIndex = oldIndex + 1
		}
		
		if currentQueue.indices.contains(newIndex) {
			currentIndex = newIndex
		} else {
			currentIndex = nil
		}
		
		return currentIndex.flatMap { currentQueue[$0] }
	}
	
	mutating
	func previousTrack() -> NFTTrack? {
		guard !currentQueue.isEmpty, let oldIndex = currentIndex, oldIndex > 0 else { return try! currentTrack() }
		
		let newIndex: Int
		switch repeatMode {
		case .none, .one:
			newIndex = max(0, oldIndex - 1)
		case .all:
			newIndex = (oldIndex - 1 + currentQueue.count) % currentQueue.count
		}
		
		if currentQueue.indices.contains(newIndex) {
			currentIndex = newIndex
		} else {
			currentIndex = nil
		}
		return currentQueue.indices.contains(newIndex) ? currentQueue[newIndex] : nil
	}
	
	mutating
	private func applyFiltersToCurrentQueue() {
		guard let filter = filters?.filter else { return }
		let prevItem = try! currentTrack()
		currentQueue = Array(originalTracks).filter(filter)
		if let prevItem {
			currentIndex = currentQueue.firstIndex(of: prevItem)
		}
	}
	
	mutating
	private func applySortingToCurrentQueue() {
		let prevItem = try! currentTrack()
		currentQueue.sort(by: sortCriteria.comparator)
		if let prevItem {
			currentIndex = currentQueue.firstIndex(of: prevItem)
		}
		print("sorted: \(currentQueue.map(\.id))")
	}
	
	mutating
	func cycleRepeatMode() {
		repeatMode = {
			switch repeatMode {
			case .all:
				return .one
			case .one:
				return .none
			case .none:
				return .all
			}
		}()
	}
}

extension PlayQueue {
	enum PlayQueueError: Error {
		case queueIsEmpty
		case invalidIndex
	}
	
	struct Filters {
		var text: String?
		@UserDefault(defaultValue: 30) var duration: Int?
		
		var filter: ((NFTTrack) -> Bool)? {
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
	
	enum RepeatMode: Codable {
		case none, one, all
	}
}
