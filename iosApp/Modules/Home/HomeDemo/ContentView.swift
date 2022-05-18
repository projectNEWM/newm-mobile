//
//  ContentView.swift
//  HomeDemo
//
//  Created by Marty Ulrich on 3/14/22.
//

import SwiftUI
import Home

struct ContentView: View {
    var body: some View {
		HomeView()
			.preferredColorScheme(.dark)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
