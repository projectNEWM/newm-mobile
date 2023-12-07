import Foundation
import shared
import ModuleLinker

public extension FileManagerService {
	func getFile(forTrack track: NFTTrack, progress: @escaping ProgressHandler) async throws -> URL {
		try await getFile(for: URL(string: track.songUrl)!, progress: progress)
	}
}
