//
//  iosAppUITests.swift
//  iosAppUITests
//
//  Created by Jose Mateo on 1/23/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import XCTest
import iosApp

class iosAppUITests: XCTestCase {
    
    var app: XCUIApplication!

    override func setUpWithError() throws {
        try super.setUpWithError()
        continueAfterFailure = false
        app = XCUIApplication()
        app.launch()
    }

    func testTapOnTribeButtonSwitchesToTribeView() throws {
        tapBottomBarButton(label: "Tribe")
        
        XCTAssertTrue( app.staticTexts["Tribe"].exists)
    }
    
    func testTapOnWalletButtonSwitchesToWalletView() throws {
        tapBottomBarButton(label: "Wallet")
        
        XCTAssertTrue( app.staticTexts["Wallet"].exists)
    }
    
    func testTapOnStarsButtonSwitchesToStarsView() throws {
        tapBottomBarButton(label: "Stars")
        
        XCTAssertTrue( app.staticTexts["Stars"].exists)
    }
    
    fileprivate func tapBottomBarButton(label: String) {
        let tabBar = app.tabBars["Tab Bar"]
        tabBar.buttons[label].tap()
    }
    
}
