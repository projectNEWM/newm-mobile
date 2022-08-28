import SwiftUI

extension Image {
	static var stars: Image { Image("Stars Icon", bundle: Bundle(for: SongPlayingModule.self)) }
	static var airplay: Image { Image("Airplay Icon", bundle: Bundle(for: SongPlayingModule.self)) }
	static var share: Image { Image("Share Icon", bundle: Bundle(for: SongPlayingModule.self)) }
	static var shuffle: Image { Image("Shuffle Icon", bundle: Bundle(for: SongPlayingModule.self)) }
	static var play: Image { Image("Play Icon", bundle: Bundle(for: SongPlayingModule.self)) }
	static var pause: Image { Image("Pause Icon", bundle: Bundle(for: SongPlayingModule.self)) }
	static var previous: Image { Image("Previous Icon", bundle: Bundle(for: SongPlayingModule.self)) }
	static var next: Image { Image("Next Icon", bundle: Bundle(for: SongPlayingModule.self)) }
	static var repeatAll: Image { Image("Repeat all Icon", bundle: Bundle(for: SongPlayingModule.self)) }
	static var tipping: Image { Image("Tipping Icon", bundle: Bundle(for: SongPlayingModule.self)) }
}
