import Foundation
import SwiftUI

public struct IDLink<LinkedView: DataView>: View {
	let selectedID: String?
	
	public init(selectedID: String?) {
		self.selectedID = selectedID
	}
	
	public var body: some View {
		NavigationLink(isActive: .constant(selectedID != nil), destination: {
			if let selectedID = selectedID {
				LinkedView(id: selectedID)
			} else {
				EmptyView()
			}
		}, label: { EmptyView() })
	}
}
