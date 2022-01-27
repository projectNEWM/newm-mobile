//
//  iOSAppViewModel.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/16/22.
//

import Foundation
import Combine

class iOSAppViewModel: ObservableObject {
	private var cancellables = [AnyCancellable]()
	@Published var loggedInUser: User? = nil

	init() {
		LoggedInUserUseCase.shared.$loggedInUser.sink { [weak self] in
			self?.loggedInUser = $0
		}.store(in: &cancellables)
	}
}

struct User {
	let email: String
}
