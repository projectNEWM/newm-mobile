package com.projectnewm.util

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun ViewPager2.bindTabs(tabLayout: TabLayout) {
    TabLayoutMediator(tabLayout, this) { _, _ -> }.attach()
}

fun ViewPager2.bindTabs(
    tabLayout: TabLayout,
    tabConfigurationStrategy: TabLayoutMediator.TabConfigurationStrategy
) {
    TabLayoutMediator(tabLayout, this, tabConfigurationStrategy).attach()
}