import Foundation
import Combine
import Resolver
import ModuleLinker
import SharedUI
import shared
import AudioPlayer

@MainActor
class LibraryViewModel: ObservableObject {
	@Published var searchText: String = ""
	@Published var errors = ErrorSet()

	@Published private(set) var route: LibraryRoute?
	@Published private(set) var tracks: [NFTTrack] = []
	@Published private(set) var showLoading: Bool = true
	@Published private(set) var showXPubScanner: Bool = false
	@Published private(set) var walletIsConnected: Bool = false
	
	private var cancels: Set<AnyCancellable> = []
	
	@Injected private var walletNFTTracksUseCase: any WalletNFTTracksUseCase
	@Injected private var connectWalletXPubUseCase: any ConnectWalletUseCase
	@InjectedObject private var audioPlayer: VLCAudioPlayer
	@Injected private var logger: any ErrorReporting
	
	init() {
		walletIsConnected = connectWalletXPubUseCase.isConnected()
		
		NotificationCenter.default.publisher(for: Notification().walletConnectionStateChanged)
			.sink { [weak self] _ in
				Task {
					guard let self else { return }
					self.walletIsConnected = self.connectWalletXPubUseCase.isConnected()
					if self.connectWalletXPubUseCase.isConnected() {
						await self.refresh()
					} else {
						self.tracks = []
					}
				}
			}
			.store(in: &cancels)
		
		audioPlayer.objectWillChange
			.sink { [weak self] _ in
				self?.objectWillChange.send()
			}
			.store(in: &cancels)

		Task {
			await refresh()
		}
	}
	
	var filteredNFTTracks: [NFTTrack] {
		guard searchText.isEmpty == false else {
			return tracks
		}
		return tracks.filter {
			$0.title.localizedCaseInsensitiveContains(searchText)
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
			let tracks = try await walletNFTTracksUseCase.getAllNFTTracks()
			self.tracks = tracks
		} catch {
			logger.logError(error)
			self.errors.append(error: NEWMError(errorDescription: "An error occured.  Please try again."))
		}
	}
	
	func xPubScanned() {
		showXPubScanner = false
		showLoading = true
		Task {
			await refresh()
			showLoading = false
		}
	}
	
	func connectWallet() {
		showXPubScanner = true
	}
	
	private func downloadTrack(_ track: NFTTrack) {
		Task {
			do {
				try await audioPlayer.downloadTrack(track)
			} catch {
				let userDidCancelCode = -999
				if (error as NSError).code != userDidCancelCode {
					self.errors.append(error: NEWMError(errorDescription: "Could not download \"\(track.title)\".  Please try again later."))
				}
			}
		}
	}
	
	func removeDownloadedTrack(_ track: NFTTrack) {
		audioPlayer.removeDownloadedSong(track)
	}
	
	func trackTapped(_ track: NFTTrack) {
		if audioPlayer.playQueueIsEmpty {
			audioPlayer.setPlayQueue(tracks, playFirstTrack: false)
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
}
