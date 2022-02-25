import SwiftUI

struct LoginView: View {
	enum Field: Hashable {
		case email
		case password
	}
	
	@ObservedObject var viewModel = LoginViewModel()
	@FocusState private var focusedField: Field?
	
	var body: some View {
		ZStack {
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
		.background(background)
	}
	
	var background: some View {
		Image("login-background")
			.resizable(capInsets: EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0), resizingMode: .stretch)
			.ignoresSafeArea()
			.scaledToFill()
			.opacity(0.3)
			.background(Color.black.opacity(1.0))
	}
	
	var newmLogo: some View {
		Image("NEWM-Logo")
			.resizable()
			.frame(width: 100, height: 100, alignment: .center)
	}
	
	var title: some View {
		Text(viewModel.title)
			.font(Font.newmFontBold(ofSize: 30))
			.foregroundColor(.white)
			.lineLimit(2)
			.padding()
			.multilineTextAlignment(.center)
			.fixedSize(horizontal: false, vertical: true)
	}
	
	var emailField: some View {
		TextField(viewModel.emailPlaceholder, text: $viewModel.email)
			.formatField()
			.focused($focusedField, equals: .email)
			.onSubmit {
				focusedField = .password
			}
	}
	
	var passwordField: some View {
		SecureField(viewModel.passwordPlaceholder, text: $viewModel.password)
			.formatField()
			.focused($focusedField, equals: .password)
			.onSubmit {
				focusedField = nil
			}
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
			.accessibilityIdentifier("enterNewmButton")
			.disabled(viewModel.fieldsAreValid == false)
			.buttonStyle(.plain)
			.font(.newmFontBold(ofSize: 14))
	}
	
	var createFreeAccount: some View {
		Button(action: viewModel.createAccountTapped, label: Text(viewModel.createAccount).formatLink)
	}
}

private extension View {
	func formatField() -> some View {
		self
			.textFieldStyle(.roundedBorder)
			.padding([.leading, .trailing])
			.padding(.bottom, 5)
			.colorInvert()
			.font(.newmFont(ofSize: 14))
			.disableAutocorrection(true)
			.textInputAutocapitalization(.never)
	}
}

private extension Text {
	func formatLink() -> some View {
		self
			.underline()
			.foregroundColor(.gray)
			.font(.roboto(ofSize: 12))
	}
}

struct LoginView_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			LoginView()
				.preferredColorScheme(.dark)
			LoginView()
		}
	}
}
