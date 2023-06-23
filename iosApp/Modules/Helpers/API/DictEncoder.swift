import Foundation

enum DictEncoderError: Error {
	case failedToConvertDataToDict
}

struct DictEncoder {
	static func encodeToDictionary(_ object: Encodable) throws -> [String: String] {
		let jsonData = try JSONEncoder().encode(object)

		guard let dictionary = try JSONSerialization.jsonObject(with: jsonData, options: []) as? [String: String?] else {
			throw DictEncoderError.failedToConvertDataToDict
		}
		return dictionary.compactMapValues { $0 }
	}
}
