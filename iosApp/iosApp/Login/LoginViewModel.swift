//
//  LoginViewModel.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/16/22.
//

import Foundation
import SwiftUI

class LoginViewModel: ObservableObject {
	let title = "Welcome to NEWM"
	let emailPlaceholder = "Your Email"
	let passwordPlaceholder = "Password"
	let forgotPassword = "Uh Oh! I Forgot My Password"
	let enterNewm = "Enter NEWM"
	let createAccount = "Or Create Your Free Account"
	
	@State var email: String = ""
	@State var password: String = ""
	
	@ObservedObject var logInUseCase = LoggedInUserUseCase.shared
	
	func enterNewmTapped() {
		logInUseCase.logIn()
	}
	
	func createAccountTapped() {
		
	}
	
	func forgotPasswordTapped() {
		
	}
}
