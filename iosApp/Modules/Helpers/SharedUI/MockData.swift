import Foundation
import shared
//import Resolver
//import ModuleLinker
//import UIKit
//import SwiftUI
//import Models
//
//public class MockData {
//	public static func bigArtistCells_shuffled(seed: UInt64, onTap: @escaping (String) -> ()) -> [BigCellViewModel] {
////		var numberGen = NonRandomNumberGenerator(seed: seed)
//		return artists.map { artist in
//			BigCellViewModel(artist: artist) {
//				onTap(artist.id)
//			}
//		}//.shuffled(using: &numberGen)
//	}
//
//	public static func bigSongCells_shuffled(seed: UInt64, onTap: @escaping (String) -> ()) -> [BigCellViewModel] {
//		var numberGen = NonRandomNumberGenerator(seed: seed)
//		return songs.map { song in
//			BigCellViewModel(song: song) {
//				onTap(song.id)
//			}
//		}//.shuffled(using: &numberGen)
//	}
//
//	public static func makeArtist(name: String) -> Artist {
//		Artist(image: url(for: Asset.MockAssets.allArtists.randomElement()!), name: name, genre: Genre.allCases.randomElement()!.title, stars: 12000, id: name)
//	}
//
//	public static var artists: [Artist] = [
//		makeArtist(name: "Joey Fidelity"),
//		makeArtist(name: "Peaches n Cremed"),
//		makeArtist(name: "Judish Minister"),
//		makeArtist(name: "Death Blow"),
//		makeArtist(name: "Dirty Mafia"),
//		makeArtist(name: "Into a Downed Dream"),
//		makeArtist(name: "Jesse Simpsenson"),
//		makeArtist(name: "Ren Stimp"),
//		makeArtist(name: "The Two Brothers"),
//		makeArtist(name: "The Third Brother"),
//		makeArtist(name: "Sister Twins"),
//		makeArtist(name: "Rassle Davidoff"),
//	]
//
//	public static func roundArtistImage(uiImage: UIImage) -> some View {
//		Image(uiImage: uiImage)
//			.circleImage(size: 60)
//	}
//
//	static func makeSong(title: String, isNFT: Bool = false) -> Song {
//		let artist = artists.randomElement()!
//		let song = Song(
//			image: artist.image,
//			title: title,
//			artist: artist,
//			isNft: isNFT,
//			id: title,
//			favorited: false,
//			duration: 124,
//			genre: Genre.allCases.randomElement()!
//		)
//		return song
//	}
//
//	public static func song(withID id: String) -> Song {
//		songs.first { $0.id == id }!
//	}
//
//	public static var songs: [Song] = [
//		makeSong(title: "My Heart Hurts So Bad"),
//		makeSong(title: "In My Mind My Thoughts Are"),
//		makeSong(title: "Alleys Are Scary"),
//		makeSong(title: "Tip-top Shop Pop"),
//		makeSong(title: "Lollitards"),
//		makeSong(title: "Karaoke With Me (Carrie, Fine With You?)"),
//		makeSong(title: "Everytime I See Me"),
//		makeSong(title: "When It's Nighttime"),
//		makeSong(title: "For The First Time, For The Last Time"),
//		makeSong(title: "Blooddart"),
//		makeSong(title: "Futures of My Past Are Now My Present"),
//		makeSong(title: "Into the Realm Of Possibilities"),
//		makeSong(title: "Finite Resources"),
//		makeSong(title: "Infinite Fidelity"),
//		makeSong(title: "Jam Baby Jam"),
//		makeSong(title: "Jerry for Your Atrics")
//	]
//
//	static func makePlaylist(id: String) -> Playlist {
//		Playlist(image: "", title: "Music for Gaming", creator: user, songCount: 32, playlistId: id, genre: .rock, starCount: 13, playCount: 439)
//	}
//
//	public static var user: User {
//		User(id: "1",
//			 createdAt: "Today",
//			 firstName: "Joe",
//			 lastName: "Blow",
//			 nickname: "Joey the Blower",
//			 pictureUrl: URL(string: "https://resizing.flixster.com/xhyRkgdbTuATF4u0C2pFWZQZZtw=/300x300/v2/https://flxt.tmsimg.com/assets/p175884_k_v9_ae.jpg")!,
//			 bannerUrl: URL(string: "https://www.sonypictures.com/sites/default/files/styles/max_360x390/public/banner-images/2020-04/stepbrothers_banner_2572x1100_v2.png?h=abc6acbe&itok=7m7ZKbdz")!,
//			 email: "joe@blow.com"
//		)
//	}
//
//	public static var playlists: [Playlist] = [
//		makePlaylist(id: "1"),
//		makePlaylist(id: "2"),
//		makePlaylist(id: "3"),
//		makePlaylist(id: "4"),
//		makePlaylist(id: "5"),
//		makePlaylist(id: "6"),
//		makePlaylist(id: "7"),
//		makePlaylist(id: "8"),
//		makePlaylist(id: "9"),
//	]
//
//	public static func url(for testImage: ImageAsset) -> String {
//		guard let imageURL = NSURL(fileURLWithPath: NSTemporaryDirectory()).appendingPathComponent("\(testImage.name).png") else {
//			fatalError()
//		}
//
//		let pngData = testImage.image.pngData()
//		do { try pngData?.write(to: imageURL) } catch { }
//		return imageURL.absoluteString
//	}
//
//	public static var thisWeekCells: [ThisWeekCellModel] {
//		[
//			ThisWeekCellModel(iconImage: Asset.Media.royaltiesIcon, amountText: "$42.39", labelText: "Royalties this week"),
//			ThisWeekCellModel(iconImage: Asset.Media.earningsIcon, amountText: "+24.34%", labelText: "Earnings this week"),
//			ThisWeekCellModel(iconImage: Asset.Media.heartIcon, amountText: "+30", labelText: "Followers this week")
//		]
//	}
//}
//
//extension Asset.MockAssets {
//	public static var allArtists: [ImageAsset] = [
//		artist0,
//		artist1,
//		artist2,
//		artist3,
//		artist4,
//		artist5,
//		artist6,
//		artist7,
//		artist8,
//		artist9
//	]
//}
//
//public struct NonRandomNumberGenerator: RandomNumberGenerator {
//	let seed: UInt64
//	public func next() -> UInt64 {
//		seed
//	}
//
//	public init(seed: UInt64) {
//		self.seed = seed
//	}
//}



public extension NFTTrack {
	static let mockTracks = [
		NFTTrack(
			id: "0",
			name: "Dripdropz",
			imageUrl: "https://arweave.net/zBVmedCDTGBH06tTEA5u0aYFWkXPkr9w1GxGefxJIms",
			songUrl: "https://arweave.net/tWDP3Lr4U3vMy_iYwm-FPBa6ad0aaMNdwHa7MUAFjuo",
			duration: 30,
			artists: ["Esco"]
		),
		NFTTrack(
			id: "1",
			name: "Lost In My Own Zone",
			imageUrl: "https://arweave.net/NkVFjDc24JfU4kgKE_vgz6hnuBpx_5ics2RTrrQEP6U",
			songUrl: "https://arweave.net/VkFPK-T7xkMgblDbxjfKjI7UXeyu23AOyg7pm4VjxzY",
			duration: 30,
			artists: ["Abyss"]
		),
		NFTTrack(
			id: "2",
			name: "Sexiest Man Alive",
			imageUrl: "https://arweave.net/eYQ-pcX9qwh3KuVcdbClQQMkdgehula0ZhtSGomzdbg",
			songUrl: "https://arweave.net/KN9xqm4VroNdlgo30lA5hvkOPasgFIbSUqAVAPn8ob0",
			duration: 30,
			artists: ["Mike Lerman"]
		),
		NFTTrack(
			id: "3",
			name: "Daisuke",
			imageUrl: "https://arweave.net/GlMlqHIPjwUtlPUfQxDdX1jWSjlKK1BCTBIekXgA66A",
			songUrl: "https://arweave.net/QpgjmWmAHNeRVgx_Ylwvh16i3aWd8BBgyq7f16gaUu0",
			duration: 30,
			artists: ["Danketsu", "Mirai Music", "NSTASIA"]
		),
		NFTTrack(
			id: "4",
			name: "Love In The Water",
			imageUrl: "https://arweave.net/f5W8RZmAQimuz_vytFY9ofIzd9QpGaDIv2UXrrahTu4",
			songUrl: "https://arweave.net/DeVRF-RTkRRHoP4M-L9AjIu35ilzgclhLOrgQB2Px34",
			duration: 30,
			artists: ["NIDO"]
		),
		NFTTrack(
			id: "5",
			name: "New Song",
			imageUrl: "https://arweave.net/xUauTN89ulvWAQ2Euz12ogF3EbDaiNPQKNe0I0Ib-mA",
			songUrl: "https://arweave.net/jeVMmGLmrtV3Dn-TfPkdCAn-Qjei4A2kFUOhFuvSCKU",
			duration: 30,
			artists: ["Esco"]
		),
		NFTTrack(
			id: "6",
			name: "Bigger Dreams",
			imageUrl: "https://arweave.net/CuPFY2Ln7yUUhJX09G530kdPf93eGhAVlhjrtR7Jh5w",
			songUrl: "https://arweave.net/P141o0RDAjSYlVQgTDgHNAORQTkMYIVCprmD_dKMVss",
			duration: 30,
			artists: ["MURS"]
		),
		NFTTrack(
			id: "7",
			name: "Space Cowboy",
			imageUrl: "https://arweave.net/qog8drrF9Oa55eWclrUejI65rn29gdcDX-Bj31VwBMc",
			songUrl: "https://arweave.net/W-PMgNX28f1RE1qwLG7SIU14-NPEmatFr51-zUAsqFI",
			duration: 30,
			artists: ["JUSE"]
		),
		NFTTrack(
			id: "8",
			name: "Best Song Ever",
			imageUrl: "https://arweave.net/tlsj0fyL0BXU871-my1CvnNSBjMgXRn4zaO3taFVz3k",
			songUrl: "https://arweave.net/6HwXcWvOwfOWTljGYTlce0_zJjg1UslgkvNue6pau0E",
			duration: 30,
			artists: ["Esco"]
		),
		NFTTrack(
			id: "9",
			name: "Underdog, Pt. 2",
			imageUrl: "https://arweave.net/eYQ-pcX9qwh3KuVcdbClQQMkdgehula0ZhtSGomzdbg",
			songUrl: "https://arweave.net/Em9XiS87I9ff8Wx2WOt2GIZ650gaiRTSjkNfJdvluLs",
			duration: 30,
			artists: ["Mike Lerman"]
		),
		NFTTrack(
			id: "10",
			name: "Make It Easy",
			imageUrl: "https://arweave.net/eYQ-pcX9qwh3KuVcdbClQQMkdgehula0ZhtSGomzdbg",
			songUrl: "https://arweave.net/bWGg7d-GhU7Y3ZTp9zfpv1JYXlS7JTxqZriFHtAgLF4",
			duration: 30,
			artists: ["Mike Lerman"]
		)
	]}
