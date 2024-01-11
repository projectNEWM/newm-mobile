import Foundation
import shared
import ModuleLinker

public extension DownloadAudioFromNFTTrackUseCase {
	func getFile(forTrack track: NFTTrack, progress: @escaping (Double) -> ()) async throws -> URL {
		try await URL(string: downloadAudioFromNFTTrack(nftTrackId: track.id) {
			progress($0.doubleValue)
		})!
	}
}
