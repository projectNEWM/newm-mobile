#if DEBUG
import Foundation
import shared
import Resolver
import ModuleLinker
import UIKit
import SwiftUI

public class MockData {
	static var songCache = NSCache<NSString, Song>()
	public static var bigArtistCells: [BigCellViewModel] {
		artists.map(BigCellViewModel.init)
	}
	
	public static var compactArtistCells: [CompactCellViewModel] {
		artists.map(CompactCellViewModel.init)
	}

	public static func makeArtist(name: String) -> Artist {
		Artist(image: url(for: Asset.MockAssets.allArtists.randomElement()!), name: name, genre: Genre.allCases.randomElement()!.title, stars: 12000, id: name)
	}
	
	public static var artists: [Artist] {
		[
			makeArtist(name: "Joey Fidelity"),
			makeArtist(name: "Peaches n Cremed"),
			makeArtist(name: "Judish Minister"),
			makeArtist(name: "Death Blow"),
			makeArtist(name: "Dirty Mafia"),
			makeArtist(name: "Into a Downed Dream"),
			makeArtist(name: "Jesse Simpsenson"),
			makeArtist(name: "Ren Stimp"),
			makeArtist(name: "The Two Brothers"),
			makeArtist(name: "The Third Brother"),
			makeArtist(name: "Sister Twins"),
			makeArtist(name: "Rassle Davidoff"),
		]
	}

	public static func roundArtistImage(uiImage: UIImage) -> some View {
		Image(uiImage: uiImage)
			.circleImage(size: 60)
	}
	
	static func makeSong(title: String, isNFT: Bool = false) -> shared.Song {
		let artist = artists.randomElement()!
		let song = Song(
				image: artist.image,
				title: title,
				artist: artist,
				isNft: isNFT,
				songId: title,
				favorited: false,
				duration: 124,
				genre: Genre.allCases.randomElement()!
		)
		songCache.setObject(song, forKey: NSString(string: song.songId))
		return song
	}

	public static func song(withID id: String) -> Song {
		songCache.object(forKey: NSString(string: id)) ?? makeSong(title: id)
	}
	
	public static var songs: [shared.Song] {
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

	static public func url(for testImage: ImageAsset) -> String {
		guard let imageURL = NSURL(fileURLWithPath: NSTemporaryDirectory()).appendingPathComponent("\(testImage.name).png") else {
			fatalError()
		}

		let pngData = testImage.image.pngData()
		do { try pngData?.write(to: imageURL) } catch { }
		return imageURL.absoluteString
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
