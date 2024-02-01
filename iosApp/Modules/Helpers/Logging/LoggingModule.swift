import ModuleLinker
import Resolver
import Sentry

final public class LoggingModule: Module {
	public static var shared = LoggingModule()
	
	init() {
		SentrySDK.start { options in
			options.dsn = "https://52541278ecccb959c827308741c8fefa@o1174944.ingest.sentry.io/4505824596525056"
			options.swiftAsyncStacktraces = true
		}
	}
	
	public func registerAllServices() {
		Resolver.register {
			SentryErrorReporter() as ErrorReporting
		}
	}
}

extension LoggingModule {
#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
#endif
}
