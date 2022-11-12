import Foundation
import SwiftUI
import Resolver
import ModuleLinker
import Artist
import SwiftUINavigation

extension HomeView {
	struct Links: View {
		@Binding var route: HomeRoute?
		@Injected private var songPlayingViewProvider: SongPlayingViewProviding
		@Injected private var artistViewProvider: ArtistViewProviding
		@Injected private var playlistViewProvider: PlaylistViewProviding
		
		var body: some View {
			ZStack {
				NavigationLink(unwrapping: $route,
							   case: /HomeRoute.songPlaying,
							   destination: { $song in
					songPlayingViewProvider.songPlayingView(song: song)
				}, onNavigate: clearLinks, label: {})
				
				NavigationLink(unwrapping: $route,
							   case: /HomeRoute.artist,
							   destination: { $artistId in
					artistViewProvider.artistView(id: artistId)
				}, onNavigate: clearLinks, label: {})
				
				NavigationLink(unwrapping: $route,
							   case: /HomeRoute.playlist,
							   destination: { $playlistId in
					playlistViewProvider.playlistView(id: playlistId)
				}, onNavigate: clearLinks, label: {})
			}
		}
		
		private func clearLinks(isActive: Bool) {
			if isActive == false { route = nil }
		}
	}
}
