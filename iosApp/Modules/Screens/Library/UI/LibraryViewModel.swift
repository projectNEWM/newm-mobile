import Foundation
import Combine
import Resolver
import ModuleLinker
import SharedUI
import shared

@MainActor
class LibraryViewModel: ObservableObject {
	@Published var searchText: String = ""
	@Published private(set) var route: LibraryRoute?
	@Published private(set) var tracks: [NFTTrack] = []
	@Published private(set) var error: String?
	@Published private(set) var showLoading: Bool = true
	@Published private(set) var showXPubScanner: Bool = false
	@Published private(set) var walletIsConnected: Bool = false
	
	private var cancels: Set<AnyCancellable> = []
	
	@Injected private var walletNFTTracksUseCase: any WalletNFTTracksUseCase
	@Injected private var connectWalletXPubUseCase: any ConnectWalletUseCase
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
	}
	
	var filteredNFTTracks: [NFTTrack] {
		guard searchText.isEmpty == false else {
			return tracks
		}
		return tracks.filter {
			$0.name.localizedCaseInsensitiveContains(searchText)
		}
	}
	
	func refresh() async {
		self.error = nil
		do {
			let tracks = try await walletNFTTracksUseCase.getAllNFTTracks()
			self.tracks = tracks
		} catch {
			logger.logError(error)
			print("ERROR: \(error.kmmException)")
			self.error = "An error occured.  Please try again."
		}
		showLoading = false
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
}
