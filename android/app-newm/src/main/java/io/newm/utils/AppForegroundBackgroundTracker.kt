package io.newm.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import io.newm.shared.NewmAppLogger
import io.newm.shared.public.analytics.NewmAppEventLogger

class AppForegroundBackgroundTracker(
    private val analyticsTracker: NewmAppEventLogger,
    private val logger: NewmAppLogger
) : Application.ActivityLifecycleCallbacks {

    private val TAG: String = AppForegroundBackgroundTracker::class.java.simpleName
    private var activityReferences = 0
    private var isActivityChangingConfigurations = false

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activityReferences == 0 && !isActivityChangingConfigurations) {
            // Track initial app launch when first activity is created
            try {
                analyticsTracker.logAppLaunch()
            } catch (e: Exception) {
                logger.error(TAG, "Error tracking app launch", e)
            }
        }
        activityReferences++
    }

    override fun onActivityStarted(activity: Activity) {
        activityReferences++
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {
        activityReferences--
        isActivityChangingConfigurations = activity.isChangingConfigurations
        if (activityReferences == 0 && !isActivityChangingConfigurations) {
            // Track app closure when all activities are stopped and none are changing configurations
            try {
                analyticsTracker.logAppClose()
            } catch (e: Exception) {
                logger.error(TAG, "Error tracking app close", e)
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}