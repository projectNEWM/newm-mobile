import SwiftUI

struct ProfileImageSection: View {
	private let model: ProfileImageCellModel
	
	init(_ model: ProfileImageCellModel) {
		self.model = model
	}
	
    var body: some View {
		VStack {
			Image("\(model.profileImage)")
				.resizable()
				.circleImage(size: 100)
				.frame(alignment: .center)
				.padding(10)
			
		}
	}
}

struct ProfileImageSection_Previews: PreviewProvider {
    static var previews: some View {
		ProfileImageSection(ProfileImageCellModel(profileImage: "bowie"))
    }
}
