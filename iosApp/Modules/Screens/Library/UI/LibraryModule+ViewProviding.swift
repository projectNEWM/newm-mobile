import Foundation
import SwiftUI
import ModuleLinker

extension LibraryModule: LibraryViewProviding {
    public func libraryView() -> AnyView {
        LibraryView().erased
    }
}
