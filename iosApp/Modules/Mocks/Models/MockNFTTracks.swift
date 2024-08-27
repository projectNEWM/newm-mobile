import Foundation
import shared

public extension NFTTrack {
	static var mocks: [NFTTrack] {
		var index = 0
		return NFTTrackMocksKt.mockTracks
			.map { track in
				defer { index += 1 }
				return NFTTrack(
					id: track.id,
					policyId: track.policyId,
					title: track.title,
					assetName: track.assetName,
					amount: track.amount,
					imageUrl: Bundle(for: MocksModule.self).path(forResource: "bowie", ofType: "jpg")!,
					audioUrl: Bundle(for: MocksModule.self).path(forResource: "getSchwifty\(index)", ofType: "mp3")!,
					duration: track.duration,
					artists: track.artists,
					genres: track.genres,
					moods: track.moods, 
					isStreamToken: false,
					isDownloaded: track.isDownloaded
				)
			}
	}
}
