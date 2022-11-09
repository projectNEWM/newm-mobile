import Foundation
import ModuleLinker
import Resolver
import SwiftUI
import shared

public final class SongPlayingModule: ModuleProtocol {
	public static var shared = SongPlayingModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as SongPlayingViewProviding
		}
		
//		Resolver.register { resolver, args in
//			SongPlayingView(
//				songInfoUseCase: resolver.resolve(SongInfoUseCaseProtocol.self, args: args),
//				musicPlayerUseCase: resolver.resolve(MusicPlayerUseCaseProtocol.self, args: args)
//			)
//		}
		
		//TODO: Register real dependencies
		
		Resolver.register { resolver, args in
			//TODO: handle errors
//			try! AudioPlayerFactory().audioPlayer()
			AudioPlayerImpl.shared as (any AudioPlayer)
		}.scope(.shared)
		
//		Resolver.register { resolver, args in
//			SongInfoUseCase(id: args()) as SongInfoUseCaseProtocol
//		}
	}
}

extension SongPlayingModule: SongPlayingViewProviding {
	public func songPlayingView(id: String) -> AnyView {
		SongPlayingView(song: MockData.songInfo()).erased
	}
}

#if DEBUG
extension SongPlayingModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
#endif
