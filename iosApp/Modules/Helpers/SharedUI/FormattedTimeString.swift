import Foundation

public extension Int {
	private static let formatter = DateComponentsFormatter()
	
	var playbackTimeString: String {
		Int.formatter.allowedUnits = self > 3600 ? [.hour, .minute, .second] : [.minute, .second]
		Int.formatter.zeroFormattingBehavior = .pad
		guard let playbackTime = Int.formatter.string(from: TimeInterval(self)) else {
			//TODO:MU: Uncomment when KMM module added back
			return Int?.playbackTimePlaceholder
		}
		
		return playbackTime
	}
}

public extension Optional<Int> {
	fileprivate static var playbackTimePlaceholder: String { "--:--" }

	var playbackTimeString: String {
		self?.playbackTimeString ?? Self.playbackTimePlaceholder
	}
}

public extension Float {
	var playbackTimeString: String {
		Int(self).playbackTimeString
	}
}

public extension Double {
	var playbackTimeString: String {
		Int(self).playbackTimeString
	}
}

public extension Optional<Double> {
	var playbackTimeString: String {
		Int(self ?? 0).playbackTimeString
	}
}

public extension Int64 {
	var playbackTimeString: String {
		Int(self).playbackTimeString
	}
}
