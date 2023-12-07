import Foundation
import SwiftUI
import ModuleLinker
import Resolver
import SharedUI
import Models

class MockArtistViewUIModelProviding: ArtistViewUIModelProviding {
	func getModel(artist: Artist, actionHandler: ArtistViewActionHandling) throws -> ArtistViewUIModel {
		Self.mockUIModel(artist: artist, actionHandler: actionHandler)
	}
	
	static func mockUIModel(artist: Artist, actionHandler: ArtistViewActionHandling) -> ArtistViewUIModel {
		ArtistViewUIModel(
			title: artist.name,
			headerImageUrl: artist.image,
			profileImage: URL(string: "" /*MockData.url(for: Asset.MockAssets.artist0)*/)!,
			followSection: SupportButton.followButton(),
			supportSection: SupportButton.supportButton(),
			trackSection: CellsSectionModel(cells: [],
											MockData.bigSongCells_shuffled(seed: 1, onTap: { id in
				actionHandler.songTapped(id: id)
			})
											title: "LATEST TRACKS"
										   ),
			topSongs: CellsSectionModel(cells: [] /*MockData.bigSongCells_shuffled(seed: 2, onTap: { id in actionHandler.songTapped(id: id) })*/, title: "TOP SONGS"),
			albumSection: CellsSectionModel(cells: MockData.bigSongCells_shuffled(seed: 3, onTap: { album in
				//TODO
			}), title: "ALBUMS")
		)
	}
}
