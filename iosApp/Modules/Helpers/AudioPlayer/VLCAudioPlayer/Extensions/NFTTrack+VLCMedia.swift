import Foundation
import VLCKitSPM
import shared

extension NFTTrack {
	func vlcMedia(fileUrl: URL) -> VLCMedia {
		let media = VLCMedia(url: fileUrl)
		media.metaData.artist = artists.joined(separator: ", ")
		media.metaData.title = title
		media.metaData.artworkURL = URL(string: imageUrl)!
		return media
	}
}
