package io.newm.shared.internal.domainservices.Mocks

import io.newm.shared.internal.domainservices.HumanVerificationAction
import io.newm.shared.internal.domainservices.HumanVerificationService

class MockHumanVerificationService: HumanVerificationService {
    var setUpCalled = false
    override suspend fun setUp() {
        print("setUp called")
        if (setUpCalled) throw IllegalStateException("setUp already called")
        setUpCalled = true
    }

    var verifyCalled = false
    override suspend fun verify(action: HumanVerificationAction): String {
        print("verify called for ${action.name}")
        if (verifyCalled) throw IllegalStateException("verify already called")
        verifyCalled = true
        return "verified"
    }
}