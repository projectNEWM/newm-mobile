import Foundation
import shared

public class MockForceAppUpdateUseCase: ForceAppUpdateUseCase {
    public init() {
        
    }
    
    public func isAndroidUpdateRequired(currentAppVersion: String, humanVerificationCode: String) async throws -> KotlinBoolean {
        KotlinBoolean(bool: false)
    }
        
    public func isiOSUpdateRequired(currentAppVersion: String, humanVerificationCode: String) async throws -> KotlinBoolean {
        KotlinBoolean(bool: false)
    }
}
