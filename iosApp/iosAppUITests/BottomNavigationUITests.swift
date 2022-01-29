//
//  iosAppUITests.swift
//  iosAppUITests
//
//  Copyright © 2022 orgName. All rights reserved.
//

import XCTest
import iosApp

class BottomNavigationUITests: XCTestCase {
    
    var app: XCUIApplication!

    override func setUpWithError() throws {
        try super.setUpWithError()
        continueAfterFailure = false
        app = XCUIApplication()
        app.launch()
    }

    func testTapOnTribeButtonSwitchesToTribeView() throws {
        tapBottomBarButton(label: "Tribe")
        
        XCTAssertTrue( app.otherElements["TribeView"].exists)
    }
    
    func testTapOnWalletButtonSwitchesToWalletView() throws {
        tapBottomBarButton(label: "Wallet")
        
        XCTAssertTrue( app.otherElements["WalletView"].exists)
    }
    
    func testTapOnStarsButtonSwitchesToStarsView() throws {
        tapBottomBarButton(label: "Stars")
        
        XCTAssertTrue( app.otherElements["StarsView"].exists)
    }
    
    fileprivate func tapBottomBarButton(label: String) {
        let tabBar = app.tabBars["Tab Bar"]
        tabBar.buttons[label].tap()
    }
    
}
