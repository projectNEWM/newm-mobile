import Foundation
import Strings

extension String {
	@Localizable(bundle: .current) static var newm = "NEWM"
	@Localizable(bundle: .current) static var newmArtists = "NEWM_ARTISTS"
	@Localizable(bundle: .current) static var newmSongs = "NEWM_SONGS"
	@Localizable(bundle: .current) static var curatedPlaylists = "CURATED_PLAYLISTS"
	@Localizable(bundle: .current) static var alternative = "ALTERNATIVE"
	@Localizable(bundle: .current) static var ambient = "AMBIENT"
	@Localizable(bundle: .current) static var explore = "EXPLORE"
	@Localizable(bundle: .current) static var hipHop = "HIP_HOP"
}

extension Bundle {
	static var current: Bundle { Bundle(for: HomeModule.self) }
}
//@propertyWrapper
//struct Localizable {
//	let wrappedValue: String
//
//	init(wrappedValue: String) {
//		self.wrappedValue = Strings.Localizable.localize(wrappedValue, bundle: Bundle(for: ModuleClass.self))
//	}
//}
