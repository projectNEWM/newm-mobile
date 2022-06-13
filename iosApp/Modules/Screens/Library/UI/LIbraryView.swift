import Foundation
import SwiftUI
import Resolver
import SharedUI

struct LibraryView: View {
	@InjectedObject private var viewModel: LibraryViewModel
	
	var body: some View {
		Text(viewModel.title)
//		BigCellSection(cells: viewModel.recentlyPlayedArtists, title: viewModel.recentlyPlayedSectionTitle)
//		CompactCellSection(cells: viewModel.yourPlaylists, title: viewModel.yourPlaylistsSectionTitle)
//		BigCellSection(cells: viewModel.likedSongs, title: viewModel.likedSongsSectionTitle)
	}
}
