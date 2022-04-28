import Foundation
import ModuleLinker

extension String {
	@Localizable(HomeModule.self) static var newm = "NEWM"
	@Localizable(HomeModule.self) static var newmArtists = "NEWM_ARTISTS"
	@Localizable(HomeModule.self) static var newmSongs = "NEWM_SONGS"
	@Localizable(HomeModule.self) static var curatedPlaylists = "CURATED_PLAYLISTS"
	@Localizable(HomeModule.self) static var alternative = "ALTERNATIVE"
	@Localizable(HomeModule.self) static var ambient = "AMBIENT"
	@Localizable(HomeModule.self) static var explore = "EXPLORE"
	@Localizable(HomeModule.self) static var hipHop = "HIP_HOP"
}
