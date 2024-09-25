import Foundation
import Combine
import Resolver
import ModuleLinker
import SharedUI
import shared
import AudioPlayer
import Utilities
import SharedExtensions

@MainActor
class LibraryViewModel: ObservableObject {
	@Published var searchText: String = "" {
		didSet {
			sortAndFilterTracks()
		}
	}
	@Published var errors = ErrorSet()
	var durationFilter: Int? {
		set {
			audioPlayer.durationFilter = newValue
			sortAndFilterTracks()
		}
		get { audioPlayer.durationFilter }
	}
		
	private(set) var selectedSort: Sort {
		set {
			audioPlayer.sort = newValue
			tracks.sort(by: newValue.comparator)
		}
		get { audioPlayer.sort }
	}
	
	private var titleSort: Sort = .title(ascending: true)
	private var artistSort: Sort = .artist(ascending: true)
	private var durationSort: Sort = .duration(ascending: true)
	
	var titleSortButtonTitle: String {
		guard case .title(let ascending) = titleSort else {
			return ""
		}
		return ascending ? "Title (A to Z)" : "Title (Z to A)"
	}
	
	var artistSortButtonTitle: String {
		guard case .artist(let ascending) = artistSort else {
			return ""
		}
		return ascending ? "Artist (A to Z)" : "Artist (Z to A)"
	}
	
	var durationSortButtonTitle: String {
		guard case .duration(let ascending) = durationSort else {
			return ""
		}
		return ascending ? "Length (Shortest to Longest)" : "Length (Longest to Shortest)"
	}
	
	private func sortAndFilterTracks() {
		filteredSortedTracks = tracks
			.filter { track in
				return track.isAboveDurationFilter(durationFilter) &&
				track.containsSearchText(searchText)
			}
			.sorted(by: selectedSort.comparator)
	}
	
	@Published private(set) var route: LibraryRoute?
	@Published private(set) var filteredSortedTracks: [NFTTrack] = []
	@Published private var tracks: [NFTTrack] = [] {
		didSet {
			sortAndFilterTracks()
		}
	}
	@Published private(set) var showLoading: Bool = true
	@Published private(set) var showCodeScanner: Bool = false
	var walletIsConnected: Bool = false
	
	private var cancels: Set<AnyCancellable> = []
	
	@LazyInjected private var walletNFTTracksUseCase: any WalletNFTTracksUseCase
	@LazyInjected private var hasWalletConnectionsUseCase: any HasWalletConnectionsUseCase
	@InjectedObject private var audioPlayer: VLCAudioPlayer
	@LazyInjected private var logger: any ErrorReporting
	
	init() {
		NotificationCenter.default.publisher(for: Notification().walletConnectionStateChanged)
			.sink { [weak self] _ in
				Task {
					guard let self else { return }
					do {
						self.walletIsConnected = try await self.hasWalletConnectionsUseCase.hasWalletConnections().boolValue
						if self.walletIsConnected {
							await self.refresh()
						} else {
							self.tracks = []
						}
					} catch {
						self.errors.append(error)
					}
				}
			}
			.store(in: &cancels)
		
		audioPlayer.objectWillChange
			.receive(on: DispatchQueue.main)
			.sink { [weak self] _ in
				self?.objectWillChange.send()
			}
			.store(in: &cancels)
		
		Task { [weak self] in
			guard let self else { return }
			for await error in audioPlayer.errors.values {
				errors.append(error)
			}
		}
		
		Task { [weak self] in
			self?.walletIsConnected = try await self?.hasWalletConnectionsUseCase.hasWalletConnections().boolValue == true
			await self?.refresh()
		}
	}
	
	var showNoSongsMessage: Bool {
		tracks.isEmpty || walletIsConnected == false
	}
	
	func refresh() async {
		defer {
			showLoading = false
		}
		
		guard walletIsConnected else { return }
		
		do {
			try await walletNFTTracksUseCase.refresh()
			tracks = try await walletNFTTracksUseCase.getAllTracks()
		} catch {
			logger.logError(error)
			errors.append(NEWMError(errorDescription: "Unable to fetch songs.  Please try again."))
		}
	}
	
	func codeScanned() {
		showCodeScanner = false
		showLoading = true
		Task {
			await refresh()
			showLoading = false
		}
	}
	
	func scannerDismissed() {
		showCodeScanner = false
	}
	
	func connectWallet() {
		showCodeScanner = true
	}
	
	private func downloadTrack(_ track: NFTTrack) {
		Task {
			do {
				try await audioPlayer.downloadTrack(track)
			} catch {
				let userDidCancelCode = -999
				if (error as NSError).code != userDidCancelCode {
					self.errors.append(NEWMError(errorDescription: "Could not download \"\(track.title)\".  Please try again later."))
				}
			}
		}
	}
	
	func removeDownloadedTrack(_ track: NFTTrack) {
		audioPlayer.removeDownloadedSong(track)
	}
	
	func trackTapped(_ track: NFTTrack) {
		if audioPlayer.playQueueIsEmpty {
			audioPlayer.setTracks(Set(tracks), playFirstTrack: false)
		}
		audioPlayer.seek(toTrack: track)
	}
	
	func swipeAction(for track: NFTTrack) {
		switch downloadState(for: track) {
		case .downloaded:
			audioPlayer.removeDownloadedSong(track)
		case .downloading:
			audioPlayer.cancelDownload(track)
		case nil:
			downloadTrack(track)
		}
	}
	
	func swipeText(for track: NFTTrack) -> String {
		switch downloadState(for: track) {
		case .downloaded:
			"Delete download"
		case .downloading:
			"Cancel"
		case nil:
			"Download"
		}
	}
	
	func loadingProgress(for track: NFTTrack) -> Double? {
		audioPlayer.loadingProgress[track]
	}
	
	func trackIsPlaying(_ track: NFTTrack) -> Bool {
		audioPlayer.trackIsPlaying(track)
	}
	
	func trackIsDownloaded(_ track: NFTTrack) -> Bool {
		audioPlayer.trackIsDownloaded(track)
	}
	
	private enum DownloadState {
		case downloaded
		case downloading
	}
	
	private func downloadState(for track: NFTTrack) -> DownloadState? {
		return if trackIsDownloaded(track) {
			.downloaded
		} else if loadingProgress(for: track) == nil {
			nil
		} else {
			.downloading
		}
	}
	
	func titleSortTapped() {
		guard case .title(let ascending) = selectedSort else {
			selectedSort = titleSort
			return
		}
		
		titleSort = .title(ascending: !ascending)
		selectedSort = titleSort
	}
	
	func artistSortTapped() {
		guard case .artist(let ascending) = selectedSort else {
			selectedSort = artistSort
			return
		}
		
		artistSort = .artist(ascending: !ascending)
		selectedSort = artistSort
	}
	
	func durationSortTapped() {
		guard case .duration(let ascending) = selectedSort else {
			selectedSort = durationSort
			return
		}
		
		durationSort = .duration(ascending: !ascending)
		selectedSort = durationSort
	}
	
	func toggleLengthFilter() {
		if durationFilter == 30 {
			durationFilter = nil
		} else {
			durationFilter = 30
		}
	}
}

extension LibraryViewModel {
	var titleSortSelected: Bool {
		return if case .title = selectedSort {
			true
		} else {
			false
		}
	}
	
	var artistSortSelected: Bool {
		return if case .artist = selectedSort {
			true
		} else {
			false
		}
	}
	
	var durationSortSelected: Bool {
		return if case .duration = selectedSort {
			true
		} else {
			false
		}
	}
}
