import XCTest
import iosApp

class BottomNavigationUITests: XCTestCase {
    
    var app: XCUIApplication!

    override func setUpWithError() throws {
        try super.setUpWithError()
        continueAfterFailure = true
        app = XCUIApplication()
        setupSnapshot(app)
        app.launch()
        
        app.buttons["enterNewmButton"].tap()
    }

    func testTapOnTribeButtonSwitchesToTribeView() throws {
        tapBottomBarButton(label: "Tribe")
        
        snapshot("Tribe")
    }
    
    func testTapOnWalletButtonSwitchesToWalletView() throws {
        tapBottomBarButton(label: "Wallet")
        
        snapshot("Wallet")
	}
    
    func testTapOnStarsButtonSwitchesToStarsView() throws {
        tapBottomBarButton(label: "Stars")
        
        snapshot("Stars")
    }
    
    fileprivate func tapBottomBarButton(label: String) {
        let tabBar = app.tabBars["Tab Bar"]
        tabBar.buttons[label].tap()
    }
    
}
