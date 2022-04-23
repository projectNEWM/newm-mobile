import Foundation
import Strings

extension String {
	@Localizable static var likeTheArtist = "LIKE_THE_SONG_SUPPORT_THE_ARTIST"
}

class ModuleClass {}

@propertyWrapper
struct Localizable {
	let wrappedValue: String
	
	init(wrappedValue: String) {
		self.wrappedValue = Strings.Localizable.localize(wrappedValue, bundle: Bundle(for: ModuleClass.self))
	}
}
