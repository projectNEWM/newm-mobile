import Foundation
import VLCKitSPM

extension Foundation.Notification {
	var vlcPlayer: VLCMediaPlayer? {
		object as? VLCMediaPlayer
	}
}
