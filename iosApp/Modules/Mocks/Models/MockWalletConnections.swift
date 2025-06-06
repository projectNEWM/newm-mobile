import shared

public extension WalletConnection {
	static func mocks(numberOfConnections: Int) -> [WalletConnection] {
		func randomString(length: Int) -> String {
			let characters = "abcdef0123456789"
			return String((0..<length).compactMap { _ in characters.randomElement() })
		}
		return (0..<numberOfConnections).map { id in
			WalletConnection(id: "Wallet Wallet Wallet Wallet Wallet Wallet Wallet Walletlkjasdfl\(id)", createdAt: "\(id)", stakeAddress: randomString(length: 20))
		}
	}
}
