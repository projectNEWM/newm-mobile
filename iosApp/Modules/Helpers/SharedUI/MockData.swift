#if DEBUG
import Foundation
import shared
import Resolver
import ModuleLinker
import UIKit
import SwiftUI

enum Genre: CaseIterable {
	case rock
	case classical
	case opera
	case country
	case beeboxin
	case scat
	
	var title: String {
		switch self {
		case .beeboxin: return "Beeboxin"
		case .classical: return "Classical"
		case .country: return "Country"
		case .rock: return "Rock"
		case .scat: return "Scat"
		case .opera: return "Opera"
		}
	}
}

public class MockData {
	public static var bigArtistCells: [BigCellViewModel] {
		models.map(BigCellViewModel.init)
	}
	
	public static var compactArtistCells: [CompactCellViewModel] {
		models.map(CompactCellViewModel.init)
	}

	public static func makeArtist(name: String, id: String) -> Artist {
		Artist(image: "", name: name, genre: Genre.allCases.randomElement()!.title, stars: 12000, id: id)
	}
	
	public static var models: [Artist] {
		[
			makeArtist(name: "Joey Fidelity", id: "1"),
			makeArtist(name: "Peaches n Cremed", id: "2"),
			makeArtist(name: "Judish Minister", id: "3"),
			makeArtist(name: "Death Blow", id: "4"),
			makeArtist(name: "Dirty Mafia", id: "5"),
			makeArtist(name: "Into a Downed Dream", id: "6"),
			makeArtist(name: "Jesse Simpsenson", id: "7"),
			makeArtist(name: "Ren Stimp", id: "8"),
			makeArtist(name: "The Two Brothers", id: "9"),
			makeArtist(name: "The Third Brother", id: "10"),
			makeArtist(name: "Sister Twins", id: "11"),
			makeArtist(name: "Rassle Davidoff", id: "12")
		]
	}
	
	public static var artistImage: UIImage {
		Asset.MockAssets.allArtists.first!.image
	}
	
	public static var roundArtistImage: some View {
		Image(uiImage: artistImage)
			.circleImage(size: 60)
	}
	
	static func makeSong(title: String, isNFT: Bool = false) -> Song {
		Song(image: "", title: title, artist: makeArtist(name: "David Bowtie", id: "1"), isNft: isNFT, songId: title)
	}
	
	public static var songs: [Song] {
		[
			makeSong(title: "My Heart Hurts So Bad"),
			makeSong(title: "In My Mind My Thoughts Are"),
			makeSong(title: "Alleys Are Scary", isNFT: true),
			makeSong(title: "Tip-top Shop Pop"),
			makeSong(title: "Lollitards"),
			makeSong(title: "Karaoke With Me (Carrie, Fine With You?)")
		]
	}
	
	static func makePlaylist(id: String) -> Playlist {
		Playlist(image: "", title: "Music for Gaming", creator: User(userName: "NEWM User"), songCount: 32, playlistId: id, genre: "Rock", starCount: 13, playCount: 439)
	}
	
	public static var playlists: [Playlist] {
		[
			makePlaylist(id: "1"),
			makePlaylist(id: "2"),
			makePlaylist(id: "3"),
			makePlaylist(id: "4"),
			makePlaylist(id: "5"),
			makePlaylist(id: "6"),
			makePlaylist(id: "7"),
			makePlaylist(id: "8"),
			makePlaylist(id: "9"),
		]
	}
}

extension Asset.MockAssets {
	public static var allArtists: [ImageAsset] {
		[
			artist0,
			artist1,
			artist2,
			artist3,
			artist4,
			artist5,
			artist6,
			artist7,
			artist8,
			artist9
		]
	}
}
#endif
