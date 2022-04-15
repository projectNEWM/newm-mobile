import Foundation
import Strings

extension String {
	@Localizable static var newm = "NEWM"
	@Localizable static var newmArtists = "NEWM_ARTISTS"
	@Localizable static var newmSongs = "NEWM_SONGS"
	@Localizable static var curatedPlaylists = "CURATED_PLAYLISTS"
	@Localizable static var alternative = "ALTERNATIVE"
	@Localizable static var ambient = "AMBIENT"
	@Localizable static var explore = "EXPLORE"
	@Localizable static var hipHop = "HIP_HOP"
}

class ModuleClass {}

@propertyWrapper
struct Localizable {
	let wrappedValue: String
	
	init(wrappedValue: String) {
		self.wrappedValue = Strings.Localizable.localize(wrappedValue, bundle: Bundle(for: ModuleClass.self))
	}
	
	static func localize(_ string: String, bundle: Bundle) -> String {
		NSLocalizedString(string, tableName: nil, bundle: bundle, value: "", comment: "")
	}
}
