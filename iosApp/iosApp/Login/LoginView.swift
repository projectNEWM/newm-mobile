//
//  LoginView.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/16/22.
//

import SwiftUI

struct LoginView: View {
	@ObservedObject var viewModel = LoginViewModel()
	
	var body: some View {
		ZStack {
			Image("login-background")
				.resizable(capInsets: EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0), resizingMode: .stretch)
				.ignoresSafeArea()
				.scaledToFill()
			VStack {
				VStack {
					newmLogo
					title
				}
				Spacer()
				VStack {
					emailField
					passwordField
					forgotPassword
					enterNewmButton
					createFreeAccount
				}
			}
			.padding(20)
		}
	}
	
	var newmLogo: some View {
		Image("NEWM-Logo")
			.resizable()
			.frame(width: 100, height: 100, alignment: .center)
	}
	
	var title: some View {
		Text(viewModel.title)
			.font(Font.system(.title).bold())
			.foregroundColor(.white)
			.lineLimit(2)
	}
	
	var emailField: some View {
		TextField(viewModel.emailPlaceholder, text: $viewModel.email)
			.formatField()
	}
	
	var passwordField: some View {
		SecureField(viewModel.passwordPlaceholder, text: $viewModel.password)
			.formatField()
	}
	
	var forgotPassword: some View {
		Button(action: viewModel.forgotPasswordTapped, label: Text(viewModel.forgotPassword).formatLink)
			.padding(.bottom, 50)
	}
	
	var enterNewmButton: some View {
		Button(viewModel.enterNewm, action: viewModel.enterNewmTapped)
			.padding()
			.padding([.leading, .trailing], 40)
			.foregroundColor(.white)
			.background(LinearGradient(colors: [.orange, .red], startPoint: .top, endPoint: .bottom))
			.cornerRadius(10)
			.padding(.bottom)
	}
	
	var createFreeAccount: some View {
		Button(action: viewModel.createAccountTapped, label: Text(viewModel.createAccount).formatLink)
	}
}

private extension View {
	func formatField() -> some View {
		self
			.padding(12)
			.background(Color.white)
			.cornerRadius(10)
			.padding([.leading, .trailing], 30)
			.padding([.top, .bottom], 5)
	}
}

private extension Text {
	func formatLink() -> some View {
		self
			.underline()
			.foregroundColor(.gray)
	}
}

struct LoginView_Previews: PreviewProvider {
	static var previews: some View {
		LoginView()
	}
}
