import Foundation
import shared
import Resolver
import Utilities

@MainActor
class WalletManagementViewModel: ObservableObject {
	@Published var walletConnections: [WalletConnection] = []
	@Published var errors = ErrorSet()
	@Published var isLoading = false
	
	@LazyInjected private var syncWalletConnectionsUseCase: any SyncWalletConnectionsUseCase
	@LazyInjected private var connectedWalletsUseCase: any GetWalletConnectionsUseCase
	@LazyInjected private var disconnectWalletsUseCase: any DisconnectWalletUseCase

	init() {
		Task { [weak self] in
			await self?.refresh()
		}
	}
	
	func refresh(force: Bool = false, isPullToRefresh: Bool = false) async {
		guard isLoading == false || force == true else { return }
		
		if isPullToRefresh == false {
			isLoading = true
		}
		do {
			walletConnections = try await syncWalletConnectionsUseCase.syncWalletConnectionsFromNetworkToDevice()
		} catch {
			errors.append(error)
		}
		isLoading = false
	}
	
	func disconnectWallet(_ walletConnection: WalletConnection) async {
		isLoading = true
		do {
			try await disconnectWalletsUseCase.disconnect(walletConnectionId: walletConnection.id)
			await refresh(force: true)
		} catch {
			errors.append(error)
		}
		isLoading = false
	}
}
