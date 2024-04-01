import Foundation
import VLCKitSPM

class VLCAudioPlayerDelegate: NSObject, VLCMediaPlayerDelegate {
	var stream: AsyncStream<Void> {
		AsyncStream { [weak self] continuation in
			self?.continuation = continuation
		}
	}

	private var continuation: AsyncStream<Void>.Continuation!
	
	func mediaPlayerStateChanged(_ aNotification: Foundation.Notification) {
		continuation.yield()
	}
	
	func mediaPlayerTimeChanged(_ aNotification: Foundation.Notification) {
		continuation.yield()
	}
	
	func mediaPlayerTitleChanged(_ aNotification: Foundation.Notification) {
		continuation.yield()
	}
	
	func mediaPlayerChapterChanged(_ aNotification: Foundation.Notification) {
		continuation.yield()
	}
	
	deinit {
		continuation.finish()
	}
}
