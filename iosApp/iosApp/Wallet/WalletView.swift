//
//  WalletView.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/31/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct WalletView: View {
	enum TimeSpan: CaseIterable, Hashable {
		case ever
		case oneYear
		case sixMonths
		case thirtyDays
		case sevenDays
		case twentyFourHours
	}

	@State var selectedTimeSpan: TimeSpan = .sixMonths
	
    var body: some View {
		timeSpanSelector
    }
	
	private var timeSpanSelector: some View {
		HStack {
			ForEach(TimeSpan.allCases, id: \.hashValue) { timeSpan in
				if timeSpan == selectedTimeSpan {
					GradientTag(title: timeSpan)
				} else {
					Text(timeSpan.description)
						.foregroundColor(.gray)
						.fontWeight(.semibold)
				}
			}
			.frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
		}
		.padding()
	}
}

extension WalletView.TimeSpan: CustomStringConvertible {
	var description: String {
		switch self {
		case .ever:
			return NSLocalizedString("Ever", comment: "")
		case .oneYear:
			return NSLocalizedString("1Y", comment: "")
		case .sixMonths:
			return NSLocalizedString("6M", comment: "")
		case .thirtyDays:
			return NSLocalizedString("30D", comment: "")
		case .sevenDays:
			return NSLocalizedString("7D", comment: "")
		case .twentyFourHours:
			return NSLocalizedString("24H", comment: "")
		}
	}
}

struct WalletView_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			WalletView()
			WalletView(selectedTimeSpan: .oneYear)
		}
		.preferredColorScheme(.dark)
		
		Group {
			WalletView()
			WalletView(selectedTimeSpan: .oneYear)
		}
		.preferredColorScheme(.light)
	}
}
