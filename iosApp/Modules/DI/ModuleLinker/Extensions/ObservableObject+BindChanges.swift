import Foundation
import Combine

public extension ObservableObject where Self.ObjectWillChangePublisher == ObservableObjectPublisher {
	func bind<Object: ObservableObject>(
		to observableObject: Object,
		storedIn set: inout Set<AnyCancellable>
	) {
		observableObject.objectWillChange
			.sink { [weak self] _ in
				self?.objectWillChange.send()
			}
			.store(in: &set)
	}
}
