import Foundation

enum LoginRoute {
	case createAccount
	case confirmationCode(_ password: String)
}
