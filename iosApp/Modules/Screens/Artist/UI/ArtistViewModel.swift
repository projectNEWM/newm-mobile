import Foundation
import UIKit
import Combine
import Resolver
import ModuleLinker
import SharedUI
import AudioPlayer
import Models

protocol ArtistRepo {
	func artist(_ id: String) async -> Artist
}

class MockArtistRepo: ArtistRepo {
	func artist(_ id: String) async -> Artist {
		MockData.artists.first { $0.id == id }!
	}
}

@MainActor
class ArtistViewModel: ObservableObject {
	@Published var state: ViewState<(ArtistViewUIModel, ArtistViewActionHandling)> = .loading
	@Published var route: ArtistRoute?
	@Injected private var audioPlayer: AudioPlayerImpl
	@Injected private var artistRepo: ArtistRepo
	private let artistId: String
	
	@Injected private var uiModelProvider: ArtistViewUIModelProviding

	init(artistId: String) {
		self.artistId = artistId
		Task {
			await refresh()
		}
	}
	
	func refresh() async {
		do {
			state = .loading
			let uiModel = try uiModelProvider.getModel(artist: await artistRepo.artist(artistId), actionHandler: self)
			state = .loaded((uiModel, self))
		} catch {
			state = .error(error)
		}
	}
}

extension ArtistViewModel: ArtistViewActionHandling {
	func albumTapped(id: String) {
		route = .album(id: id)
	}
	
	func songTapped(id: String) {
		audioPlayer.song = MockData.song(withID: id)
		audioPlayer.playbackInfo.isPlaying = true
	}
	
	func songPlayingTapped(id: String) {
		route = .songPlaying(id: id)
	}
}
