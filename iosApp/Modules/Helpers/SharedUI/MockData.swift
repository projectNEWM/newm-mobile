//import Foundation
//import shared
////import Resolver
////import ModuleLinker
////import UIKit
////import SwiftUI
////import Models
////
////public class MockData {
////	public static func bigArtistCells_shuffled(seed: UInt64, onTap: @escaping (String) -> ()) -> [BigCellViewModel] {
//////		var numberGen = NonRandomNumberGenerator(seed: seed)
////		return artists.map { artist in
////			BigCellViewModel(artist: artist) {
////				onTap(artist.id)
////			}
////		}//.shuffled(using: &numberGen)
////	}
////
////	public static func bigSongCells_shuffled(seed: UInt64, onTap: @escaping (String) -> ()) -> [BigCellViewModel] {
////		var numberGen = NonRandomNumberGenerator(seed: seed)
////		return songs.map { song in
////			BigCellViewModel(song: song) {
////				onTap(song.id)
////			}
////		}//.shuffled(using: &numberGen)
////	}
////
////	public static func makeArtist(name: String) -> Artist {
////		Artist(image: url(for: Asset.MockAssets.allArtists.randomElement()!), name: name, genre: Genre.allCases.randomElement()!.title, stars: 12000, id: name)
////	}
////
////	public static var artists: [Artist] = [
////		makeArtist(name: "Joey Fidelity"),
////		makeArtist(name: "Peaches n Cremed"),
////		makeArtist(name: "Judish Minister"),
////		makeArtist(name: "Death Blow"),
////		makeArtist(name: "Dirty Mafia"),
////		makeArtist(name: "Into a Downed Dream"),
////		makeArtist(name: "Jesse Simpsenson"),
////		makeArtist(name: "Ren Stimp"),
////		makeArtist(name: "The Two Brothers"),
////		makeArtist(name: "The Third Brother"),
////		makeArtist(name: "Sister Twins"),
////		makeArtist(name: "Rassle Davidoff"),
////	]
////
////	public static func roundArtistImage(uiImage: UIImage) -> some View {
////		Image(uiImage: uiImage)
////			.circleImage(size: 60)
////	}
////
////	static func makeSong(title: String, isNFT: Bool = false) -> Song {
////		let artist = artists.randomElement()!
////		let song = Song(
////			image: artist.image,
////			title: title,
////			artist: artist,
////			isNft: isNFT,
////			id: title,
////			favorited: false,
////			duration: 124,
////			genre: Genre.allCases.randomElement()!
////		)
////		return song
////	}
////
////	public static func song(withID id: String) -> Song {
////		songs.first { $0.id == id }!
////	}
////
////	public static var songs: [Song] = [
////		makeSong(title: "My Heart Hurts So Bad"),
////		makeSong(title: "In My Mind My Thoughts Are"),
////		makeSong(title: "Alleys Are Scary"),
////		makeSong(title: "Tip-top Shop Pop"),
////		makeSong(title: "Lollitards"),
////		makeSong(title: "Karaoke With Me (Carrie, Fine With You?)"),
////		makeSong(title: "Everytime I See Me"),
////		makeSong(title: "When It's Nighttime"),
////		makeSong(title: "For The First Time, For The Last Time"),
////		makeSong(title: "Blooddart"),
////		makeSong(title: "Futures of My Past Are Now My Present"),
////		makeSong(title: "Into the Realm Of Possibilities"),
////		makeSong(title: "Finite Resources"),
////		makeSong(title: "Infinite Fidelity"),
////		makeSong(title: "Jam Baby Jam"),
////		makeSong(title: "Jerry for Your Atrics")
////	]
////
////	static func makePlaylist(id: String) -> Playlist {
////		Playlist(image: "", title: "Music for Gaming", creator: user, songCount: 32, playlistId: id, genre: .rock, starCount: 13, playCount: 439)
////	}
////
////	public static var user: User {
////		User(id: "1",
////			 createdAt: "Today",
////			 firstName: "Joe",
////			 lastName: "Blow",
////			 nickname: "Joey the Blower",
////			 pictureUrl: URL(string: "https://resizing.flixster.com/xhyRkgdbTuATF4u0C2pFWZQZZtw=/300x300/v2/https://flxt.tmsimg.com/assets/p175884_k_v9_ae.jpg")!,
////			 bannerUrl: URL(string: "https://www.sonypictures.com/sites/default/files/styles/max_360x390/public/banner-images/2020-04/stepbrothers_banner_2572x1100_v2.png?h=abc6acbe&itok=7m7ZKbdz")!,
////			 email: "joe@blow.com"
////		)
////	}
////
////	public static var playlists: [Playlist] = [
////		makePlaylist(id: "1"),
////		makePlaylist(id: "2"),
////		makePlaylist(id: "3"),
////		makePlaylist(id: "4"),
////		makePlaylist(id: "5"),
////		makePlaylist(id: "6"),
////		makePlaylist(id: "7"),
////		makePlaylist(id: "8"),
////		makePlaylist(id: "9"),
////	]
////
////	public static func url(for testImage: ImageAsset) -> String {
////		guard let imageURL = NSURL(fileURLWithPath: NSTemporaryDirectory()).appendingPathComponent("\(testImage.name).png") else {
////			fatalError()
////		}
////
////		let pngData = testImage.image.pngData()
////		do { try pngData?.write(to: imageURL) } catch { }
////		return imageURL.absoluteString
////	}
////
////	public static var thisWeekCells: [ThisWeekCellModel] {
////		[
////			ThisWeekCellModel(iconImage: Asset.Media.royaltiesIcon, amountText: "$42.39", labelText: "Royalties this week"),
////			ThisWeekCellModel(iconImage: Asset.Media.earningsIcon, amountText: "+24.34%", labelText: "Earnings this week"),
////			ThisWeekCellModel(iconImage: Asset.Media.heartIcon, amountText: "+30", labelText: "Followers this week")
////		]
////	}
////}
////
////extension Asset.MockAssets {
////	public static var allArtists: [ImageAsset] = [
////		artist0,
////		artist1,
////		artist2,
////		artist3,
////		artist4,
////		artist5,
////		artist6,
////		artist7,
////		artist8,
////		artist9
////	]
////}
////
////public struct NonRandomNumberGenerator: RandomNumberGenerator {
////	let seed: UInt64
////	public func next() -> UInt64 {
////		seed
////	}
////
////	public init(seed: UInt64) {
////		self.seed = seed
////	}
////}
