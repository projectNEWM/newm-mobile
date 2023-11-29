import Foundation
import Combine
import Resolver
import ModuleLinker
import SharedUI
import AudioPlayer

@MainActor
class LibraryViewModel: ObservableObject {
	@Published var route: LibraryRoute?
	@InjectedObject private var audioPlayer: VLCAudioPlayer
}
