import Foundation
import shared
import ModuleLinker

public extension FileManagerService {
	func getPlaybackURL(forTrack track: NFTTrack) async throws -> URL {
		try getPlaybackURL(for: URL(string: track.audioUrl)!)
	}
	
	func download(track: NFTTrack, progressHandler: @escaping (Double) -> Void) async throws {
		try await download(url: URL(string: track.audioUrl)!, progressHandler: progressHandler)
	}
	
	func cancelDownload(track: NFTTrack) {
		cancelDownload(url: URL(string: track.audioUrl)!)
	}
}
